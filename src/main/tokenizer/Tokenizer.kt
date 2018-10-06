package tokenizer

class Tokenizer {

    private val statementRegex = "\n".toRegex()

    //TODO - lorenzodb1: Assumption on order of SCHEDULE, LET and FOR! Must find a way not to hardcode it!
    fun tokenize(statement: String): String {
        val splitStatement: Array<String> = statement.split(statementRegex).toTypedArray()

        return ""
    }

    private fun tokenizeSchedule() {

    }

    private fun tokenizeLet() {

    }

    private fun tokenizeFor() {

    }

}