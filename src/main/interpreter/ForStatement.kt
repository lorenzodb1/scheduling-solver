package interpreter

import utils.Constant.STATEMENT_DIVIDER_REGEX
import utils.Grammar.isForKey
import utils.Grammar.isInKey
import utils.Grammar.isValidId


class ForStatement(forString: String) : Statement(forString) {

    lateinit var id: IdNode
    lateinit var nodeSet: NodeSet
    lateinit var statements: StatementList

    init {
        // TODO: Delete these, just setting so it compiles
        id = IdNode("");
        nodeSet = NodeSet("");
        statements = StatementList("");

        val forStatementIterator: Iterator<String> = forString.split(STATEMENT_DIVIDER_REGEX).iterator()
        val key: String = forStatementIterator.next()
        while (forStatementIterator.hasNext()) {
            println(forStatementIterator.next())
        }
    }

    override fun interp() {

    }
}
