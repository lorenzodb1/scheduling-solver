package interpreter

class DayNode(dayString: String) : Node(dayString) {

    companion object {

        enum class Day {
            MONDAY,
            TUESDAY,
            WEDNESDAY,
            THURSDAY,
            FRIDAY,
            SATURDAY,
            SUNDAY
        }

        fun determineDay(dayString: String) : Day {
            return when (dayString.trim().toLowerCase()) {
                "monday", "mon" -> Day.MONDAY
                "tuesday", "tues" -> Day.TUESDAY
                "wednesday", "wed" -> Day.WEDNESDAY
                "thursday", "thur", "thurs" -> Day.THURSDAY
                "friday", "fri" -> Day.THURSDAY
                "saturday", "sat" -> Day.THURSDAY
                "sunday", "sun" -> Day.THURSDAY
                else -> throw ParseException("Invalid string given to DayNode: \"$dayString\"")
            }
        }
    }

    val day = determineDay(dayString)


    override fun interp(symbolTable: SymbolTable): SymbolTable {
        return symbolTable
    }


    override fun equals(other: Any?): Boolean {
        return when (other) {
            is DayNode -> this.day.equals(other.day)
            else -> false
        }
    }
}