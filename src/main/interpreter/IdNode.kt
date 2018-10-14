package interpreter

class IdNode(idString: String) : Node(idString) {

    var id: String;

    init {
        val trimmedIdString = idString.trim()
        // TODO: check if reserved word?
        if (trimmedIdString.length < 2 || trimmedIdString[0] != '$' || trimmedIdString.contains(' ')){
            throw ParseException("Invalid string given to IdNode: \"$trimmedIdString\"")
        }

        id = trimmedIdString.substring(1)
    }

    override fun interp(symbolTable: SymbolTable): SymbolTable {
        TODO()
    }

    // TODO: Lots of copy-pasta between `equals` methods, can we use DRY?
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is IdNode -> other.id.equals(this.id)
            else -> false
        }
    }
}