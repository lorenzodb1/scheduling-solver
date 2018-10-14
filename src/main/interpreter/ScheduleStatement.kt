package interpreter

import java.util.*
import java.util.Collections.sort

val <T> List<T>.tail: List<T>
  get() = drop(1)

val <T> List<T>.head: T
  get() = first()

class ScheduleStatement(scheduleString: String) : Statement(scheduleString) {

    // TODO: Delete me
    //SCHEDULE := SCHEDULE <NODE> AT <NODE> [FOR <NODE>] [AT LOCATION <NODE>] [ON [EVERY ]<NODE> [AND <NODE>]*  [UNTIL <NODE>]][WITH (<NODE>|<NODESET>)]

    // The string representation of ScheduleStatement that we're passed at parse time.
    // We hold this and delay parsing until interp. time so that we can do variable substitution first
    lateinit var savedScheduleString: String

    var description: String? = null
    var time: TimeNode? = null
    var duration: DurationNode? = null
    var location: LocationNode? = null
    // NOTE: These array not nullable because comparing nullable arrays is a pain in the ass
    // and having them null doesn't really add any semantic meaning for us over them beeing empty
    var dates: Array<java.util.Date> = arrayOf()
    var guests: Array<GuestNode> = arrayOf()

    init {
        savedScheduleString = scheduleString
    }

    // Parse the given SCHEDULE statement and populate member variables appropriately
    private fun parse(str: String) {
        val phrases = splitOnAndKeepWords(str, arrayOf(
                "SCHEDULE", "AT", "FOR", "IN", "ON", "WITH"
        )).map { s -> s.split(' ') }

        // There must be at least two phrases, "SCHEDULE ..." and "AT ..."
        if (phrases.size < 2) {
            throw InterpException("Required elements (SCHEDULE or AT) not found in string passed to ScheduleStatement: \"$str\"")
        }

        // First word of first phrase must be "SCHEDULE"
        if (phrases[0].size < 2 || phrases[0][0] != "SCHEDULE") {
            throw InterpException("ScheduleStatement must start with \"SCHEDULE\": \"$str\"")
        }
        description = phrases[0].tail.joinToString(" ")

        // TODO: We should also probably be checking for duplicates here
        // Parse all the possible additions to SCHEDULE
        for (phrase in phrases.tail) {
            val phrase_tail = phrase.tail.joinToString(" ")
            when (phrase[0]) {
                "AT" -> {
                    when (time) {
                        null -> time = TimeNode(phrase_tail)
                        else -> throw InterpException(
                                "Duplicate AT statement found in string given to ScheduleStatement: $str")
                    }
                }
                "FOR" -> {
                    when (duration) {
                        null -> duration = DurationNode(phrase_tail)
                        else -> throw InterpException(
                                "Duplicate FOR statement found in string given to ScheduleStatement: $str")
                    }
                }
                "IN" -> {
                    when (location) {
                        null -> location = LocationNode(phrase_tail)
                        else -> throw InterpException(
                                "Duplicate IN statement found in string given to ScheduleStatement: $str")
                    }
                }
                "ON" -> dates += parseDates(phrase_tail)
                "WITH" -> guests += parseEmails(phrase_tail)
                else -> "Could not match keyword at start of phrase in ScheduleStatement: \"$phrase\". " +
                        "This probably means there's something wrong with the code"
            }
        }
    }

    override fun interp(symbolTable: SymbolTable): SymbolTable {
        val substitutedScheduleString = subsituteVariables(savedScheduleString, symbolTable)
        parse(substitutedScheduleString)
        return symbolTable
    }

    // TODO: Delete these two functions if not used or move to util class and remove from ForStatement to stop DRY
    /**
     * Keeps iterating until it hits a non-empty string or the end
     *
     * If the non-empty string is the given keyword (ignoring leading and trailing spaces),
     * then returns true, else returns false
     */
    private fun iterateOverWhitespace(iter: Iterator<IndexedValue<String>>, keyword: String): Boolean {
        while (iter.hasNext()){
            val currentToken = iter.next().value
            if (currentToken.trim() != "") {
                return currentToken.trim() == keyword
            }
        }
        return false
    }

    /**
     * Keeps iterating until it hits a non-empty string or the end
     *
     * Returns the first non-empty string with the index (index, token) or null if at the end
     */

    private fun iterateOverWhitespace(iter: Iterator<IndexedValue<String>>): IndexedValue<String>? {
        while (iter.hasNext()) {
            val (currentIndex, currentToken) = iter.next()
            if (currentToken.trim() != "") {
                return IndexedValue(currentIndex, currentToken)
            }
        }
        return null
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is ScheduleStatement -> {
                this.description.equals(other.description) &&
                this.time == other.time &&
                this.duration == other.duration &&
                this.location == other.location &&
                this.dates.contentDeepEquals(other.dates) &&
                this.guests.contentDeepEquals(other.guests)
            }
            else -> false
        }
    }

    companion object {

        // Substitute all variables in the given string and return the substituted string
        fun subsituteVariables(str: String, symbolTable: SymbolTable): String {
            // Substitute all the variables in the symbol table
            var mutableStr = str
            for (symbol in symbolTable) {
                val key = symbol.key
                val value = symbol.value
                val regex = Regex("\\\$" + key)
                mutableStr = mutableStr.replace(regex, value)
            }

            // Check to make sure everything was substituted
            if (mutableStr.contains('$')) {
                throw InterpException("No known value for at least on variable in string passed to ScheduleStatement: $str")
            }

            return mutableStr
        }

        // Split the given string at every string in the given list, but keep the value we split on to the right of the split
        // ie. "A B C D E F G" split on ["A", "D", "F"] should be ["A B C", "D E", "F G"]. Should always be stable, and
        // assumes that the word given is ane
        fun splitOnAndKeepWords(inputStr: String, splitVals: Array<String>): Array<String> {
            var result = mutableListOf("")
            val tokens = inputStr.split(" ")
            val iter = tokens.iterator()

            while (iter.hasNext()) {
                val currToken = iter.next()
                if (splitVals.contains(currToken)) {
                    result.add("")
                }
                result[result.lastIndex] += ("$currToken ")
            }
            result = result.map { s -> s.trim() }.toMutableList()
            result = result.filter { s -> s != "" }.toMutableList()
            return result.toTypedArray()
        }

        // Parses a date string. This string could be anything in an "ON" phrase in a "SCHEDULE" statement
        fun parseDates(dateString: String): Array<Date> {
            var parts = dateString.split(" UNTIL ").toMutableList()
            if (parts.size < 1) {
                throw InterpException("Invalid datestring \"$dateString\" given to ScheduleStatement")
            }
            if (parts.size > 2) {
                throw InterpException("Multiple UNTIL statements in single ON statement in \"$dateString\" given to ScheduleStatement")
            }

            // Ignore EVERY if it exists
            parts[0] = parts[0].trim().removePrefix("EVERY")

            // Figure out what the days and dates are
            var dayNodes = mutableListOf<DayNode>()
            var dateNodes = mutableListOf<DateNode>()
            val days_or_dates = parts[0].split(" AND ")
            for (day_or_date in days_or_dates) {
                try {
                    dayNodes.add(DayNode(day_or_date))
                } catch (dayException: ParseException) {
                    try {
                        dateNodes.add(DateNode(day_or_date))
                    } catch (dateException: ParseException) {
                        throw InterpException("\"$day_or_date\" passed to ScheduleStatement is neither a weekday nor a date")
                    }
                }
            }

            val dates = mutableListOf<Date>()
            when (parts.size) {
                1 -> {
                    // "UNTIL" phrase IS NOT present
                    for (dayNode in dayNodes) {
                        dates.add(getFirstInstanceOfWeekdayAfterOrOnDate(CurrentDate.date, dayNode.day))
                    }
                    for (dateNode in dateNodes) {
                        dates.add(getFirstInstanceOfMonthAndDayAfterOrOnDate(
                                CurrentDate.date, dateNode.month.month, dateNode.dayOfMonth))
                    }
                }
                2 -> {
                    // "UNTIL" phrase IS present
                    val endDateNode = DateNode(parts[1])
                    val endDate = getFirstInstanceOfMonthAndDayAfterOrOnDate(
                            CurrentDate.date, endDateNode.month.month, endDateNode.dayOfMonth)
                    for (dayNode in dayNodes) {
                        var date = CurrentDate.date
                        while (true) {
                            // Keep adding a week until we hit the endDate
                            val weekDayDate = getFirstInstanceOfWeekdayAfterOrOnDate(date, dayNode.day)
                            if (weekDayDate <=  endDate)
                                dates.add(weekDayDate)
                            else
                                break
                            date = addWeekToDate(date)
                        }
                    }
                    for (dateNode in dateNodes) {
                        val date = CurrentDate.date
                        while (date <= endDate) {
                            // Keep adding a year until we hit the endDate
                            dates.add(getFirstInstanceOfMonthAndDayAfterOrOnDate(
                                    CurrentDate.date, dateNode.month.month, dateNode.dayOfMonth))
                            date.year += 1
                        }
                    }
                }
            }

            sort(dates)

            return dates.toTypedArray()
        }

        // Returns the given date with one week added to it
        fun addWeekToDate(date: Date) : Date {
            return Date(date.time + 7 * 24 * 3600 * 1000)
        }

        // Gets the date of the next given weekday, relative to the given date
        // Will return `afterOrOnDate` if it is on `weekday`
        fun getFirstInstanceOfWeekdayAfterOrOnDate(afterOrOnDate: Date, weekday: Int): Date {
            // Figure out what the date for this WeekDay should be, relative to the current date
            val calendar = Calendar.getInstance()
            calendar.setTime(afterOrOnDate)
            val currWeekday = calendar.get(Calendar.DAY_OF_WEEK)
            val days = (Calendar.SATURDAY + weekday - currWeekday) % 7
            calendar.add(Calendar.DAY_OF_YEAR, days)
            return calendar.time
        }

        // Gets the first instance of a given Month/Day after a given date
        fun getFirstInstanceOfMonthAndDayAfterOrOnDate(afterDate: Date, month: Int, dayOfMonth: Int): Date {
            // Check if this date has already passed and add a year if it has
            var date = Date(afterDate.year, month, dayOfMonth)
            if (month < afterDate.month || (month == afterDate.month && dayOfMonth < afterDate.day)) {
                date.year += 1
            }
            return date
        }

        // Parse out email(s) in a nodeset
        fun parseEmails(emailsString: String): Array<GuestNode> {
            return emailsString.split(" AND ").map { emailString -> GuestNode(emailString) }.toTypedArray()
        }
    }
}
