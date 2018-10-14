package interpreter

import java.util.Calendar

class DayNode(dayString: String) : Node(dayString) {

    companion object {

        fun determineCalendar(dayString: String) : Int {
            return when (dayString.trim().toLowerCase()) {
                "monday", "mon" -> Calendar.MONDAY
                "tuesday", "tues" -> Calendar.TUESDAY
                "wednesday", "wed" -> Calendar.WEDNESDAY
                "thursday", "thur", "thurs" -> Calendar.THURSDAY
                "friday", "fri" -> Calendar.FRIDAY
                "saturday", "sat" -> Calendar.SATURDAY
                "sunday", "sun" -> Calendar.SUNDAY
                else -> throw ParseException("Invalid string given to CalendarNode: \"$dayString\"")
            }
        }
    }

    val day = determineCalendar(dayString)


    override fun interp(symbolTable: SymbolTable) {
    }


    override fun equals(other: Any?): Boolean {
        return when (other) {
            is DayNode -> this.day.equals(other.day)
            else -> false
        }
    }
}