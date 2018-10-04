class MonthNode(s: String) : Node(s) {

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

    val month = determineMonth(s)


    public override fun interp() {
        //TODO: implement
    }

    companion object {
        fun determineMonth(monthString: String) : Month {
            //TODO: implement
            return Month.JANUARY
        }
    }
}