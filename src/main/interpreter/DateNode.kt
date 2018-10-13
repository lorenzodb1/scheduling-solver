package interpreter

import java.time.Month

class DateNode(dateString: String) : Node(dateString) {

    lateinit var month: MonthNode
    var dayOfMonth: Int = 0 //TODO: make into a node?

    init {
        // TODO: Init properly
        month = MonthNode("January")

        //TODO: Given dateString, parse and set values for month and dayOfMonth.

        val dateStringTrimmedAndLowerCase = dateString.toLowerCase().trim()

        var monthString: String? = null
        var dayOfMonthString: String? = null

        //<MONTH> <Num>[st|th]
        var matches = Regex("^([A-z]+)\\s+(\\d+)(?:st|nd)?$")
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

    public override fun interp() {
        //TODO: implement
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is DateNode -> {
                this.month.equals(other.month) &&
                this.dayOfMonth.equals(other.dayOfMonth)
            }
            else -> false
        }
    }
}