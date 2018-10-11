package interpreter

class DurationNode(s: String) : Node(s) {
    var seconds: Int = 0

    public override fun interp() {
        //TODO: implement
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is DurationNode -> this.seconds.equals(other.seconds)
            else -> false
        }
    }
}