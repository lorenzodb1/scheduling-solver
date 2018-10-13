package interpreter

class LocationNode(locationString: String) : Node(locationString) {

    lateinit var location: String;

    init {
        location = locationString
    }

    public override fun interp() {
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is LocationNode -> this.location.equals(other.location)
            else -> false
        }
    }
}