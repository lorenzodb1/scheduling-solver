package interpreter

class LocationNode(locationString: String) : Node(locationString) {

    // TODO: determine proper datatype to represent location
    lateinit var location: String;

    init {
        // TODO: Properly init (do we even want to parse this??)
        location = ""
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