package interpreter

import java.util.Calendar

class MonthNode(monthString: String) : Node(monthString) {

    companion object {
        fun determineMonth(monthString: String) : Int {
            return when (monthString.trim().toLowerCase()) {
                "january", "jan" -> Calendar.JANUARY
                "february", "feb" -> Calendar.FEBRUARY
                "march", "mar" -> Calendar.MARCH
                "april", "apr" -> Calendar.APRIL
                "may" -> Calendar.MAY
                "june", "jun" -> Calendar.JUNE
                "july", "jul" -> Calendar.JULY
                "august", "aug" -> Calendar.AUGUST
                "september", "sept" -> Calendar.SEPTEMBER
                "october", "oct" -> Calendar.OCTOBER
                "november", "nov" -> Calendar.NOVEMBER
                "december", "dec" -> Calendar.DECEMBER
                else -> throw ParseException("Invalid string given to MonthNode: \"$monthString\"")
            }
        }
    }

    val month = determineMonth(monthString)

    override fun interp(symbolTable: SymbolTable): SymbolTable {
        return symbolTable
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is MonthNode -> this.month.equals(other.month)
            else -> false
        }
    }
}