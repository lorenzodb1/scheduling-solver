class ForStatement(forString: String) : Statement(forString) {

    lateinit var id: IdNode
    lateinit var nodeSet: NodeSet
    lateinit var statements: StatementList

    init {
        //TODO: parse forString and initialize properties
    }

    override public fun interp() {
        // TODO: implement
    }
}