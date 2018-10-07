package tokenizer

import interpreter.ForStatement
import interpreter.LetStatement
import interpreter.ScheduleStatement

class Tokenizer {

    private val statementDividerRegex: Regex = Regex("""\s""")

    //TODO - lorenzodb1: What should we do with the result of ScheduleStatement(partialStatement.toString())?
    fun tokenize(statement: String): Unit {
        val statementIterator: Iterator<String> = statement.split(statementDividerRegex).iterator()
        var key = statementIterator.next()
        while (statementIterator.hasNext()) {
            val partialStatement = StringBuilder("")
            if (Grammar.isLetKey(key)) {
                do {
                    partialStatement.append(key)
                    key = statementIterator.iterator().next()
                } while (!Grammar.isStatementKey(key) && statementIterator.hasNext())
                LetStatement(partialStatement.toString())
                continue
            } else if (Grammar.isScheduleKey(key)) {
                do {
                    partialStatement.append(key)
                    key = statementIterator.iterator().next()
                } while (!Grammar.isStatementKey(key) && statementIterator.hasNext())
                ScheduleStatement(partialStatement.toString())
                continue
            } else if (Grammar.isForKey(key)) {
                var nestedForCounter = 0
                do {
                    partialStatement.append(key)
                    key = statementIterator.iterator().next()
                    if (Grammar.isForKey(key)) nestedForCounter += 1
                    if (Grammar.isEndForKey(key)) nestedForCounter -= 1
                } while (!Grammar.isEndForKey(key) && nestedForCounter == 0 && statementIterator.hasNext())
                ForStatement(partialStatement.toString())
                continue
            } else {
                //TODO - lorenzodb1: Throw an exception
            }
        }
    }
}
