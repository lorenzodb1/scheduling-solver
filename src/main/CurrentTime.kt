import interpreter.Node
import interpreter.TimeNode

class CurrentTime {
    companion object {
        fun getDate(): TimeNode {
            return TimeNode("8am")
        }
    }
}