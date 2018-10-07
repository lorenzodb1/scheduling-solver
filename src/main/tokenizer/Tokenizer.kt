package tokenizer

class Tokenizer {

    private val statementDividerRegex = " ".toRegex()

    fun tokenize(statement: String): String {
        val statementArray: Array<String> = statement.split(statementDividerRegex).toTypedArray()
        for (key in statementArray) {
            if () {
                
            }
        }

        return ""
    }

    private fun tokenizeSchedule() {

    }

    private fun tokenizeLet() {

    }

    private fun tokenizeFor() {

    }

}