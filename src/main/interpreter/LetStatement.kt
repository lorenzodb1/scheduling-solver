package interpreter

class LetStatement(letString: String): Statement(letString) {

    lateinit var id: String
    lateinit var value: Node

    init {
        //parse statment further and interpret
        private val statementDividerRegex: Regex = Regex("""\s""")

    }

    public override fun interp() {
        //TODO: implement
    }
}