package interpreter

import utils.Constant.STATEMENT_DIVIDER_REGEX
import utils.Grammar.isLetKey

class LetStatement(letString: String): Statement(letString) {

    lateinit var id: IdNode
    lateinit var value: String

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
        val valueTokens = tokens[1].trim().split(' ')
        if (valueTokens.size != 1){
            throw ParseException("Spaces in value in string passed to LetStatement: \"$letString\"")
        }
        value = valueTokens[0]
    }

    override fun interp() {

    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is LetStatement -> {
                this.id.equals(other.id) &&
                this.value.equals(other.value)
            }
            else -> false
        }
    }
}
