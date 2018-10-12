package interpreter

class LocationNode(locationString: String) : Node(locationString) {

    // TODO: determine proper datatype to represent location
    var location: String = ""

    init {
        // TODO: Properly init (do we even want to parse this??)
    }

    override fun interp() {

    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is LocationNode -> this.location.equals(other.location)
            else -> false
        }
    }
}