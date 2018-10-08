package interpreter

import utils.Constants.STATEMENT_DIVIDER_REGEX
import utils.Grammar.isLetKey

class LetStatement(letString: String): Statement(letString) {

    lateinit var id: String
    lateinit var value: Node

    init {
        val letStatementIterator = letString.split(STATEMENT_DIVIDER_REGEX).iterator()
        val key = letStatementIterator.next()
        while (letStatementIterator.hasNext()) {
            if (isLetKey(key)) {
                id = letStatementIterator.next()
                val equals = letStatementIterator.next() //TODO - lorenzodb1: Assumption that the format is $X = 5
                if (true) {
                    //value = Node(letStatementIterator.next()) TODO - lorenzodb1 - Node is abstract!
                }//TODO - leticianakajima: else, there is no equals sign here, throw an error
                //TODO - leticianakajima: else, there is no "Let", throw an error
            }
        }
    }

    override fun interp() {
        //TODO: implement
    }
}
