package interpreter

class IdNode(val idString: String) : Node(idString) {

    lateinit var id: String;

    init {
        // TODO: check if reserved word?
        if (idString.length < 2 || idString[0] != '$'){
            throw ParseException("Invalid string given to IdNode: \"" + idString + "\"")
        }

        id = idString.substring(1)
    }

    public override fun interp() {
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