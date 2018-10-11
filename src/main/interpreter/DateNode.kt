package interpreter
import utils.Constant.STATEMENT_DIVIDER_REGEX
import utils.Grammar.isvalidMonth

class DateNode(dateString: String) : Node(dateString) {

    lateinit var month: MonthNode
    var dayOfMonth: Int = 0 //TODO: make into a node?

    //form: <MONTH> <Num>[st|nd|rd|th] | <Num>[st|nd|rd|th] OF <MONTH>
    //Jan 10th, OR 20 OF January
    init {
        val dateIterator = dateString.split(STATEMENT_DIVIDER_REGEX).iterator()
        val key = dateIterator.next()
        while (dateIterator.hasNext()) {
            if (isvalidMonth(key)) {
                //first case is a month
                month = MonthNode(key)
                val numberString = dateIterator.next() //this has to be the number
                dayOfMonth = numberString.substring(0, numberString.length - 2).toInt()
            } else {
                //the number comes first
                dayOfMonth = key.substring(0, key.length - 2).toInt()
                dateIterator.next()
                month = MonthNode(dateIterator.next())
            }
        }
    }

    override fun interp() {
        //TODO: implement
    }
}
