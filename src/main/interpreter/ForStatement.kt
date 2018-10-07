package interpreter

import Utils.STATEMENT_DIVIDER_REGEX
import tokenizer.Grammar.isForKey
import tokenizer.Grammar.isInKey
import tokenizer.Grammar.isValidId


class ForStatement(forString: String) : Statement(forString) {

    lateinit var id: IdNode
    lateinit var nodeSet: NodeSet
    lateinit var statements: StatementList

    init {
        val forStatementIterator: Iterator<String> = forString.split(STATEMENT_DIVIDER_REGEX).iterator()
        val key: String = forStatementIterator.next()
        while (forStatementIterator.hasNext()) {
            if (isForKey(key)) {
                val idKey = forStatementIterator.next()
                if (isValidId(idKey)) {
                    id = IdNode(idKey)
                }
                else {
                    //TODO - lorenzodb1: Throw exception
                }
                val inKey: String = forStatementIterator.next()
                if (isInKey(inKey)) {
                    val rangeString: String = forStatementIterator.next()
                    nodeSet = NodeSet(rangeString) //TODO - lorenzodb1: Are brackets included in rangeString or nah?
                }
                val forLoopBody = StringBuilder("")
                while (forStatementIterator.hasNext()) forLoopBody.append(forStatementIterator.next())
                statements = StatementList(forLoopBody.toString())
            }
        }
    }

    override fun interp() {

    }
}
