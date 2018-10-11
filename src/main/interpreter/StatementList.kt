package interpreter

import utils.Constant.STATEMENT_DIVIDER_REGEX
import utils.Grammar.isEndForKey
import utils.Grammar.isForKey
import utils.Grammar.isLetKey
import utils.Grammar.isScheduleKey
import utils.Grammar.isStatementKey

class StatementList(statementsString: String) {

    lateinit var statements: MutableList<Statement>

    init {
        var statementStrings = statementsString.split("LET", "SCHEDULE", "FOR");

        // TODO
    }

    fun interp() {

    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is StatementList -> this.statements.equals(other.statements)
            else -> false
        }
    }
}
