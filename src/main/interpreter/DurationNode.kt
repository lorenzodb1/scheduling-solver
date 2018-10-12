package interpreter

import utils.Constant.STATEMENT_DIVIDER_REGEX
import utils.Grammar.isValidDuration

class DurationNode(durationString: String) : Node(durationString) {

    var seconds: Int = 0

    //DURATION := <Num> (hours|minutes|seconds)

    init {
        val durationIterator = durationString.split(STATEMENT_DIVIDER_REGEX).iterator()
        val key: String = durationIterator.next()
        while (durationIterator.hasNext()) {
            val digit: Int = key.toIntOrNull()
                    ?: throw ParseException("") //TODO - lorenzodb1: Write message
            val magnitude = durationIterator.next()
            if(isValidDuration(magnitude)) {
                if (magnitude == "seconds") {
                    seconds = digit
                } else if (magnitude == "minutes") {
                    seconds = (digit * 60)
                } else if (magnitude == "hours") {
                    seconds = (digit * 3600)
                }
            }
        }
    }

    override fun interp() {
        //TODO: implement
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is DurationNode -> this.seconds.equals(other.seconds)
            else -> false
        }
    }
}
