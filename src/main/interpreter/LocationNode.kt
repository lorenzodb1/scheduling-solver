package interpreter

class LocationNode(locationString: String) : Node(locationString) {

    var location: String = locationString

    override fun interp(symbolTable: SymbolTable): SymbolTable {
        return symbolTable
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is LocationNode -> this.location.equals(other.location)
            else -> false
        }
    }
}
