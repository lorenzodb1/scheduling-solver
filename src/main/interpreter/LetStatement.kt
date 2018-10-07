package interpreter

class LetStatement(letString: String): Statement(letString) {

    lateinit var id: String
    lateinit var value: Node

    private val statementDividerRegex: Regex = Regex("""\s""")

    init {
        val letIterator = forString.split(statementDividerRegex).iterator()
        val word = letIterator.next()
        while (letIterator.hasnext()) {
            if (isLetKey(word)) {
                id = IdNode(word.next())
                val equals = letIterator.next()
                if (isEquals(equals)) {
                    value = letIterator.next()
                }//TODO: else, there is no equals sign here, throw an error
                //TODO: else, there is no "Let", throw an error

            }


        }
    }

    public override fun interp() {
        //TODO: implement
    }
}


