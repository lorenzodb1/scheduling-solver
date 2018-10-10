package interpreter
import utils.Constant.STATEMENT_DIVIDER_REGEX

class DateNode(dateString: String) : Node(dateString) {

    lateinit var month: MonthNode
    var dayOfMonth: Int = 0 //TODO: make into a node?

    //form: <MONTH> <Num>[st|th] | <Num>[st|th] OF <MONTH>
    //Jan 10th, OR 20 OF January
    init {
        val dateIterator = letString.split(STATEMENT_DIVIDER_REGEX).iterator()
        val key = dateIterator.next()
        while (dateIterator.hasNext()) {
            if (isMonthArray(key)) {
                //first case is a month
                month = key
                val next = dateIterator.next() //this has to be the number
                val remove_st = next.split("st")
                val remove_th = next.split("th")
                val number = remove_th
                dayOfMonth = number

            } else { //the number comes first
                val day = key
                val remove_st = day.split("st")
                val remove_th = day.split("th")
                val number = remove_th
                dayOfMonth = number
                val for_word = dateIterator.next()
                month = dateIterator.next()
            }


    }


    public override fun interp() {
        //TODO: implement
    }

}