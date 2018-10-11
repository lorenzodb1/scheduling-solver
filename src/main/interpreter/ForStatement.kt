package interpreter

class ForStatement(forString: String) : Statement(forString) {

    lateinit var id: IdNode
    lateinit var nodeSet: NodeSet
    lateinit var statements: StatementList

    init {
        var trimmedForString = forString.trim()

        // Split on keywords
        var forStringSplit = trimmedForString.split("FOR", "IN", "ENDFOR", "DO");
        if ( forStringSplit.size < 5 || forStringSplit.first() != "" || forStringSplit.last() != ""){
            throw ParseException("Invalid string given to ForStatement: \"$forString\"")
        }

        // Remove empty start/end strings
        forStringSplit = forStringSplit.subList(1, forStringSplit.lastIndex)

        id = IdNode(forStringSplit[0])
        nodeSet = NodeSet(forStringSplit[1])
        statements = StatementList(forStringSplit[2])
    }

    override fun interp() {

    }
}
