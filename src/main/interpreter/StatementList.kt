package interpreter

import utils.Constant.STATEMENT_DIVIDER_REGEX
import utils.Grammar.isEndForKey
import utils.Grammar.isForKey
import utils.Grammar.isLetKey
import utils.Grammar.isScheduleKey
import utils.Grammar.isStatementKey

class StatementList(statement: String) {

    lateinit var statements: MutableList<Statement>

    init {
        // TODO: Properly init
        statements = arrayListOf()

        val statementIterator: Iterator<String> = statement.split(STATEMENT_DIVIDER_REGEX).iterator()
        var key: String = statementIterator.next()
        while (statementIterator.hasNext()) {
            val partialStatement = StringBuilder("")
            if (isLetKey(key)) {
                do {
                    partialStatement.append(key)
                    key = statementIterator.next()
                } while (!isStatementKey(key) && statementIterator.hasNext())
                statements.add(LetStatement(partialStatement.toString()))
                continue
            } else if (isScheduleKey(key)) {
                do {
                    partialStatement.append(key)
                    key = statementIterator.next()
                } while (!isStatementKey(key) && statementIterator.hasNext())
                statements.add(ScheduleStatement(partialStatement.toString()))
                continue
            } else if (isForKey(key)) {
                var nestedForCounter = 0
                do {
                    partialStatement.append(key)
                    key = statementIterator.next()
                    if (isForKey(key)) nestedForCounter += 1
                    if (isEndForKey(key)) nestedForCounter -= 1
                } while (!isEndForKey(key) && nestedForCounter == 0 && statementIterator.hasNext())
                statements.add(ForStatement(partialStatement.toString()))
                continue
            } else {
                //TODO - lorenzodb1: Throw an exception
            }
        }
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
