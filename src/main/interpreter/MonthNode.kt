package interpreter

class MonthNode(monthString: String) : Node(monthString) {

    companion object {
        fun determineMonth(monthString: String) : Month {
            return when (monthString.trim().toLowerCase()) {
                "january", "jan" -> Month.JANUARY
                "febrary", "feb" -> Month.FEBRUARY
                "march", "mar" -> Month.MARCH
                "april", "apr" -> Month.APRIL
                "may" -> Month.MAY
                "june", "jun" -> Month.JUNE
                "july", "jul" -> Month.JULY
                "august", "aug" -> Month.AUGUST
                "september", "sept" -> Month.SEPTEMBER
                "october", "oct" -> Month.OCTOBER
                "november", "nov" -> Month.NOVEMBER
                "december", "dec" -> Month.DECEMBER
                else -> throw ParseException("Invalid string given to MonthNode: $monthString")
            }
        }

        enum class Month {
            JANUARY,
            FEBRUARY,
            MARCH,
            APRIL,
            MAY,
            JUNE,
            JULY,
            AUGUST,
            SEPTEMBER,
            OCTOBER,
            NOVEMBER,
            DECEMBER
        }
    }

    val month: Month = determineMonth(monthString)

    public override fun interp() {
        //TODO: implement
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is MonthNode -> this.month.equals(other.month)
            else -> false
        }
    }
}