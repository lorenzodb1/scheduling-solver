package interpreter
import interpreter.Statement
import java.util.Calendar




class ScheduleStatement(scheduleString: String) : Statement(scheduleString) {

    lateinit var description: String
    lateinit var time: TimeNode
    lateinit var duration: DurationNode
    lateinit var location: LocationNode
    lateinit var dates: Array<DateNode>
    lateinit var guests: Array<GuestNode>

    init {

        val tokens = scheduleString.split(" ");
        val tokensIter = tokens.iterator().withIndex();

        // check for schedule
        if (!iterateOverWhitespace(tokensIter, "SCHEDULE")) {
            throw ParseException("Missing SCHEDULE statement statement")
        }

        // Get the Id
        var nextNonWhitespacepiece = iterateOverWhitespace(tokensIter)
        when (nextNonWhitespacepiece) {
            null -> throw ParseException("Missing description of event")
            else -> {
                description = nextNonWhitespacepiece.value
            }
        }

        // Check that next word is "AT"
        if (!iterateOverWhitespace(tokensIter, "AT")) {
            throw ParseException("Missing AT statement")
        }

        // Get the time
        var nextNonWhitespaceToken = iterateOverWhitespace(tokensIter)
        when (nextNonWhitespaceToken) {
            null -> throw ParseException("Missing time of event")
            else -> {
                time = TimeNode(nextNonWhitespaceToken.value)
            }
        }

        // Check for keyword "for" - optinal
        if (iterateOverWhitespace(tokensIter, "FOR")) {
            // Get the duration if there is one
            when (nextNonWhitespaceToken) {
                null -> throw ParseException("Missing duration of event")
                else -> {
                    duration = DurationNode(nextNonWhitespaceToken.value)
                }
            }

        }

        // Check for keyword "in" - optional
        if (iterateOverWhitespace(tokensIter, "IN")) {
            // Get the duration if there is one
            when (nextNonWhitespaceToken) {
                null -> throw ParseException("Missing location of event")
                else -> {
                    location = LocationNode(nextNonWhitespaceToken.value)
                }
            }

        }

        //[ON [EVERY ]<NODE> [AND <NODE>]*  [UNTIL <NODE>]]

//is the word we're on "ON"
        var weekdays: MutableList<String> = mutableListOf<String>()
        val final_date: String

        if (iterateOverWhitespace(tokensIter, "ON")) {
            //next word is "EVERY"
            if (iterateOverWhitespace(tokensIter, "EVERY")) {
                when (nextNonWhitespaceToken) {
                    null -> throw ParseException("Missing week of event")
                    else -> {
                        weekdays.add(nextNonWhitespaceToken.value)
                    }
                }
            }

            while (iterateOverWhitespace(tokensIter, "AND")) {
                when (nextNonWhitespaceToken) {
                    null -> throw ParseException("Missing week of event")
                    else -> {
                        weekdays.add(nextNonWhitespaceToken.value)
                    }
                }
            }

            if (iterateOverWhitespace(tokensIter, "UNTIL")) {
                when (nextNonWhitespaceToken) {
                    null -> throw ParseException("Missing week of event")
                    else -> {
                        final_date = nextNonWhitespaceToken.value //string
                    }
                }
            }
        }


        //something that saves dates of the days you want
        val todays_date = CurrentDate.getDate()
        //would have to parse this and feed it into Calendar?


            if (iterateOverWhitespace(tokensIter, "WITH")) {
                // Get the duration if there is one
                when (nextNonWhitespaceToken) {
                    null -> throw ParseException("Missing guests of event")
                    else -> {
                        guests = arrayOf<GuestNode>()
                    }
                }

        }

    }

    override fun interp() {

    }

    /**
     * Keeps iterating until it hits a non-empty string or the end
     *
     * If the non-empty string is the given keyword (ignoring leading and trailing spaces),
     * then returns true, else returns false
     */
    private fun iterateOverWhitespace(iter: Iterator<IndexedValue<String>>, keyword: String): Boolean {
        while (iter.hasNext()){
            var currentToken = iter.next().value
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
            var (currentIndex, currentToken) = iter.next()
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
                this.time.equals(other.time) &&
                this.duration.equals(other.duration) &&
                this.location.equals(other.location) &&
                this.dates.contentDeepEquals(other.dates) &&
                this.guests.contentDeepEquals(other.guests)
            }
            else -> false
        }
    }
}