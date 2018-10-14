package interpreter

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
    var dates: Array<DateNode> = arrayOf()
    var guests: Array<GuestNode> = arrayOf()

    init {
        savedScheduleString = scheduleString
    }

    // Parse the given SCHEDULE statement and populate member variables appropriately
    fun parse(str: String) {
        val tokens = str.split(" ")
        val tokensIter = tokens.iterator().withIndex()

        // check for schedule
        if (!iterateOverWhitespace(tokensIter, "SCHEDULE")) {
            throw ParseException("Missing SCHEDULE keyword in string passed to ScheduleStatement: $str")
        }

        // Get the Id
        val nextNonWhitespacepiece = iterateOverWhitespace(tokensIter)
        when (nextNonWhitespacepiece) {
            null -> throw ParseException("Missing description in string passed to ScheduleStatement: $str")
            else -> {
                description = nextNonWhitespacepiece.value
            }
        }

        // Check that next word is "AT"
        if (!iterateOverWhitespace(tokensIter, "AT")) {
            throw ParseException("Missing AT statement")
        }

        // Get the time
        val nextNonWhitespaceToken = iterateOverWhitespace(tokensIter)
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
        val weekdays: MutableList<String> = mutableListOf<String>()
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
                    guests = arrayOf()
                }
            }
        }
    }

    override fun interp(symbolTable: SymbolTable): SymbolTable {
        val substitutedScheduleString = subsituteVariables(savedScheduleString, symbolTable)
        parse(substitutedScheduleString)
        return symbolTable
    }

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
    }
}
