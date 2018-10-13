package interpreter
import utils.Constant.STATEMENT_DIVIDER_REGEX
import utils.Grammar.isvalidMonth

import java.time.Month

class DateNode(dateString: String) : Node(dateString) {

    var month: MonthNode? = null
    var dayOfMonth: Int = 0 //TODO: make into a node?

    //form: <MONTH> <Num>[st|nd|rd|th] | <Num>[st|nd|rd|th] OF <MONTH>
    //Jan 10th, OR 20 OF January
    init {
        // TODO: Init properly
        month = MonthNode("January")

        //TODO: Given dateString, parse and set values for month and dayOfMonth.

        val dateStringTrimmedAndLowerCase = dateString.toLowerCase().trim()

        var monthString: String? = null
        var dayOfMonthString: String? = null

        //<MONTH> <Num>[st|th]
        var matches = Regex("^([A-z]+)\\s+(0?[1-9]|1[0-9]|2[0-9]|3[0-1])(?:st|nd|rd|th)?\$")
                        .matchEntire(dateStringTrimmedAndLowerCase)
        if (matches != null) {
            monthString = matches.groupValues[1]
            dayOfMonthString = matches.groupValues[2]
        }

        // <Num>[st|th] OF <MONTH>
        matches = Regex("^(\\d+)(?:st|nd)?\\s+of\\s+([A-z]+)$")
                .matchEntire(dateStringTrimmedAndLowerCase)
        if (matches != null) {
            monthString = matches.groupValues[2]
            dayOfMonthString = matches.groupValues[1]
        }

        if (monthString == null || dayOfMonthString == null) {
            throw ParseException("Could not find month and/or day of month in string given to DateNode: $dateString")
        }

        month = MonthNode(monthString)
        try {
            dayOfMonth = dayOfMonthString.toInt()
        } catch (e: Exception) {
            throw ParseException("Invalid day of month in string given to DateNode: $dateString")
        }
    }

    override fun interp() {
        //TODO: implement
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is DateNode -> {
                this.month!! == other.month &&
                this.dayOfMonth == other.dayOfMonth
            }
            else -> false
        }
    }
}
