package interpreter

class DateNode(dateString: String) : Node(dateString) {

    lateinit var month: MonthNode
    var dayOfMonth: Int = 0 //TODO: make into a node?

    init {
        //TODO: Given dateString, parse and set values for month and dayOfMonth.
    }


    public override fun interp() {
        //TODO: implement
    }

}