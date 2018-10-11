package interpreter

class DateNode(dateString: String) : Node(dateString) {

    lateinit var month: MonthNode
    var dayOfMonth: Int = 0 //TODO: make into a node?

    init {
        // TODO: Init properly
        month = MonthNode("January")

        //TODO: Given dateString, parse and set values for month and dayOfMonth.
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