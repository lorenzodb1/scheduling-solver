package interpreter

class LetStatement(letString: String): Statement(letString) {

    var id: IdNode
    var value: String

    init {
        // Check to make sure there is only on '=' sign
        val tokens = letString.split('=')
        if (tokens.size != 2) {
            throw ParseException("More or less then one \"=\" character found in string passed to LetStatement: \"$letString\"")
        }

        // Parse the id
        val letTokens = tokens[0].split(' ').filter { s: String -> s != "" };
        if (letTokens.size != 2 || letTokens[0] != "LET") {
            throw ParseException("Invalid string passed to LetStatement: \"$letString\"")
        }
        id = IdNode(letTokens[1])

        // Parse the value
        value = tokens[1].trim()

        if (value.contains('$')) {
            throw ParseException("Value given to LetStatement contains forbidden character '\$': \"$letString\"")
        }
        if (value == "") {
            throw ParseException("Empty value in string given to given to LetStatement: \"$letString\"")
        }
    }

    override fun interp(symbolTable: SymbolTable) : MutableList<ScheduleStatement> {
        symbolTable.set(id.id, value)
        return mutableListOf()
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is LetStatement -> {
                this.id.equals(other.id) &&
                this.value!! == other.value
            }
            else -> false
        }
    }
}
