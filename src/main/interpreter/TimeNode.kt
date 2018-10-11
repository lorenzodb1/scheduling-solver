package interpreter

import interpreter.Node

class TimeNode(timeString: String) : Node(timeString) {

    // assume 24 hour time:
    // hour is in range [0, 24)
    // minute is in range [0, 60)
    var hour: Int = -1
    var minute: Int = -1

    init {
        //TODO: initialize hour and minute from timeString
    }

    public override fun interp() {
        //TODO: implement
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is TimeNode -> {
                this.hour.equals(other.hour) &&
                this.minute.equals(other.minute)
            }
            else -> false
        }
    }
}