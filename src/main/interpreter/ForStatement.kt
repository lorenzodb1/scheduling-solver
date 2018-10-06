package interpreter

class ForStatement(forString: String) : Statement(forString) {

    lateinit var id: IdNode
    lateinit var nodeSet: SetNode
    lateinit var statements: Array<Statement>

    init {
        //TODO: parse forString and initialize properties
    }

    override public fun interp() {
        // TODO: implement
    }
}