class DayNode(s: String) : Node(s) {

    enum class Day {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    val day = determineDay(s)


    public override fun interp() {
        //TODO: implement
    }

    companion object {
        fun determineDay(dayString: String) : Day {
            //TODO: implement
            return Day.MONDAY
        }
    }
}