package interpreter
import IdNode
import Grammar


class ForStatement(forString: String) : Statement(forString) {

    lateinit var id: IdNode
    lateinit var nodeSet: NodeSet
    lateinit var statements: Statement

    private val statementDividerRegex: Regex = Regex("""\s""")
    private val curlyBraceRegex: Regex = Regex("\{([^}]+)\}")
    private val commaRegex: Regex = Regex("/([^,]+)/g")

    init (forString: String){
        val forStatementIterator = forString.split(statementDividerRegex).iterator()
        val word = forStatementIterator.next()
        while(forStatementIterator.hasNext()){
            if (isForKey(word)){
                id = IdNode(word.next)
            }
            if (isInKey(word)){
                rangeString = word.next().split(curlyBraceRegex)
                elemOfRange = rangeString.split(commaRegex)
                nodes = nodeSet(elemOfRange)
                statement = Statement(rangeString.next()) //whats inside of the for loop
            }
        }


    }

    override public fun interp() {
    }
}