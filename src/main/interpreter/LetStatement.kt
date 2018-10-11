package interpreter

import utils.Constant.STATEMENT_DIVIDER_REGEX
import utils.Grammar.isLetKey

class LetStatement(letString: String): Statement(letString) {

    lateinit var id: String
    lateinit var value: Node

    init {
        // TODO: Properly init
        id = ""
        value = IdNode("\$X")
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
