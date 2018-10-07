package tokenizer

import Utils.STATEMENT_DIVIDER_REGEX
import interpreter.ForStatement
import interpreter.LetStatement
import interpreter.ScheduleStatement
import tokenizer.Grammar.isEndForKey
import tokenizer.Grammar.isForKey
import tokenizer.Grammar.isLetKey
import tokenizer.Grammar.isScheduleKey
import tokenizer.Grammar.isStatementKey

class Tokenizer {

    //TODO - lorenzodb1: What should we do with the return value of ScheduleStatement(partialStatement.toString())?
    fun tokenize(statement: String): Unit {
        val statementIterator: Iterator<String> = statement.split(STATEMENT_DIVIDER_REGEX).iterator()
        var key = statementIterator.next()
        while (statementIterator.hasNext()) {
            val partialStatement = StringBuilder("")
            if (isLetKey(key)) {
                do {
                    partialStatement.append(key)
                    key = statementIterator.next()
                } while (!isStatementKey(key) && statementIterator.hasNext())
                LetStatement(partialStatement.toString())
                continue
            } else if (isScheduleKey(key)) {
                do {
                    partialStatement.append(key)
                    key = statementIterator.next()
                } while (!isStatementKey(key) && statementIterator.hasNext())
                ScheduleStatement(partialStatement.toString())
                continue
            } else if (isForKey(key)) {
                var nestedForCounter = 0
                do {
                    partialStatement.append(key)
                    key = statementIterator.next()
                    if (isForKey(key)) nestedForCounter += 1
                    if (isEndForKey(key)) nestedForCounter -= 1
                } while (!isEndForKey(key) && nestedForCounter == 0 && statementIterator.hasNext())
                ForStatement(partialStatement.toString())
                continue
            } else {
                //TODO - lorenzodb1: Throw an exception
            }
        }
    }
}
