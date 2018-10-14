package interpreter

class StatementList(statementsString: String) {

    var statements: MutableList<Statement> = mutableListOf()

    // Save this for later so we can perform copying
    var statementsString = statementsString;

    companion object {
        // We use this enum when tracking what statement we're in when
        // iterating over the given string during parsing
        enum class StatementType{
            LET,
            FOR,
            SCHEDULE,
            NONE
        }
    }

    init {
        val tokens = statementsString.replace('\n', ' ').split(" ")
        val tokensIter = tokens.iterator().withIndex()

        // This tracks nest FOR loops so that we only split on the topmost level
        var forStatementDepth = 0

        var currentStatementType = StatementType.NONE
        var startOfCurrentStatementIndex = 0

        while (tokensIter.hasNext()) {
            val (currentIndex, currentToken) = tokensIter.next()
            when (currentToken.trim()) {
                "LET" -> {
                    if (forStatementDepth == 0) {
                        buildAndAddStatement(tokens, startOfCurrentStatementIndex, currentIndex, currentStatementType)
                        currentStatementType = StatementType.LET
                        startOfCurrentStatementIndex = currentIndex
                    }
                }
                "FOR" -> {
                    if (forStatementDepth == 0){
                        buildAndAddStatement(tokens, startOfCurrentStatementIndex, currentIndex, currentStatementType)
                        currentStatementType = StatementType.FOR
                        startOfCurrentStatementIndex = currentIndex
                    }
                    forStatementDepth++
                }
                "ENDFOR" -> {
                    forStatementDepth--
                }
                "SCHEDULE" -> {
                    if (forStatementDepth == 0) {
                        buildAndAddStatement(tokens, startOfCurrentStatementIndex, currentIndex, currentStatementType)
                        currentStatementType = StatementType.SCHEDULE
                        startOfCurrentStatementIndex = currentIndex
                    }
                }
                else -> {
                    // TODO: Do we even want to do anything here?
                }
            }
        }
        // Since we've stopped iterating, we're at the end of the list, build a statement with
        // anything left at the end of the string
        buildAndAddStatement(tokens, startOfCurrentStatementIndex, tokens.lastIndex+1, currentStatementType)

        // Check for unmatched FOR, ENDFOR statements
        if (forStatementDepth > 0) {
            throw ParseException("Found FOR with no matching ENDFOR in: \"$statementsString\"")
        } else if (forStatementDepth < 0) {
            throw ParseException("Found ENDFOR with no matching FOR in: \"$statementsString\"")
        }

    }

    private fun buildAndAddStatement(tokens: List<String>, startIndex: Int, endIndex: Int, statementType: StatementType) {
        // Check for empty statement
        if (startIndex == endIndex) return

        // Build the statement from the given range in the tokens
        val statementTokens = tokens.subList(startIndex, endIndex)
        val statementString = statementTokens.joinToString(" ")

        // Check for empty statement
        if (statementString.trim() == "") return

        val statement: Statement = when (statementType) {
            StatementType.LET -> LetStatement(statementString)
            StatementType.SCHEDULE -> ScheduleStatement(statementString)
            StatementType.FOR -> ForStatement(statementString)
            else -> throw ParseException("Invalid StatementType matched in StatementList: \"$statementType\" " +
                    "for string: \"${tokens.joinToString(" ")}\" " +
                    "(NOTE: This probably means something is wrong with the code)")
        }

        statements.add(statement)
    }

    fun interp(symbolTable: SymbolTable) {
        for (statement in statements) {
            statement.interp(symbolTable)
        }
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is StatementList -> this.statements.equals(other.statements)
            else -> false
        }
    }

    fun copy(): StatementList {
        return StatementList(statementsString)
    }
}
