import interpreter.Node
import interpreter.DateNode

class CurrentDate {
    companion object {
        fun getDate(): DateNode {
            return DateNode("Oct 12th")
        }
    }
}