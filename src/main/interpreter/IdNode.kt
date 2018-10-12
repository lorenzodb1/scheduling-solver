package interpreter

class IdNode(val idString: String) : Node(idString) {

    var id: String;

    init {
        val trimmedIdString = idString.trim()
        // TODO: check if reserved word?
        if (trimmedIdString.length < 2 || trimmedIdString[0] != '$'){
            throw ParseException("Invalid string given to IdNode: \"$trimmedIdString\"")
        }

        id = trimmedIdString.substring(1)
    }

    override fun interp() {
        //TODO: implement
    }

    // TODO: Lots of copy-pasta between `equals` methods, can we use DRY?
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is IdNode -> other.id.equals(this.id)
            else -> false
        }
    }
}