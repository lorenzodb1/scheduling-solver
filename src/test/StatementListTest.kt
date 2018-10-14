import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class StatementListTest {

    @Test
    fun constructStatementList_empty_list() {
        val statementListString = ""
        val statementList = StatementList(statementListString)
        assertArrayEquals(mutableListOf<Statement>().toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_single_LET_statement() {
        val statementListString = "LET \$X = 5pm"
        val statementList = StatementList(statementListString)
        assertArrayEquals(mutableListOf<Statement>(
                LetStatement("LET \$X = 5pm")
        ).toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_single_FOR_statement() {
        val statementListString = "FOR \$X IN {5pm, 6pm} SCHEDULE dinner AT \$X ENDFOR"
        val statementList = StatementList(statementListString)
        assertArrayEquals(mutableListOf<Statement>(
                ForStatement("FOR \$X IN {5pm, 6pm} SCHEDULE dinner AT \$X ENDFOR")
        ).toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_single_SCHEDULE_statement() {
        val statementListString = "SCHEDULE dinner AT \$X"
        val statementList = StatementList(statementListString)
        val expectedStatements = mutableListOf(
                ScheduleStatement("SCHEDULE dinner AT \$X")
        ).toTypedArray()
        val actualStatements = statementList.statements.toTypedArray()
        //assertTrue(expectedStatements.contentEquals(actualStatements))
        assertArrayEquals(mutableListOf(
                ScheduleStatement("SCHEDULE dinner AT \$X")
        ).toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_multiple_statements() {
        val statementListString = "LET \$X = 5pm SCHEDULE dinner AT \$X FOR \$X IN {5pm, 6pm} SCHEDULE dinner AT \$X ENDFOR"
        val statementList = StatementList(statementListString)
        assertArrayEquals(mutableListOf<Statement>(
                LetStatement("LET \$X = 5pm"),
                ScheduleStatement("SCHEDULE dinner AT \$X"),
                ForStatement("FOR \$X IN {5pm, 6pm} SCHEDULE dinner AT \$X ENDFOR")
        ).toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_multiple_statements_wierd_spacing() {
        val statementListString = "   LET \$X =  5pm        SCHEDULE dinner AT     \$X     FOR     \$X IN {5pm, 6pm} SCHEDULE dinner AT \$X ENDFOR   "
        val statementList = StatementList(statementListString)
        assertArrayEquals(mutableListOf<Statement>(
                LetStatement("LET \$X = 5pm"),
                ScheduleStatement("SCHEDULE dinner AT \$X"),
                ForStatement("FOR \$X IN {5pm, 6pm} SCHEDULE dinner AT \$X ENDFOR")
        ).toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_nested_FOR_loops() {
        val statementListString = "FOR \$X IN {5pm, 6pm} FOR \$Y IN {dinner, lunch} SCHEDULE \$Y AT \$X ENDFOR ENDFOR"
        val statementList = StatementList(statementListString)
        assertArrayEquals(mutableListOf<Statement>(
                ForStatement("FOR \$X IN {5pm, 6pm} FOR \$Y IN {dinner, lunch} SCHEDULE \$Y AT \$X ENDFOR ENDFOR")
        ).toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_unmatched_FOR() {
        val statementListString = "FOR \$X IN {5pm, 6pm} SCHEDULE dinner AT \$X"
        assertThrows(ParseException::class.java) {
            StatementList(statementListString)
        }
    }

    @Test
    fun constructStatementList_unmatched_ENDFOR() {
        val statementListString = "FOR \$X IN {5pm, 6pm} SCHEDULE dinner AT \$X"
        assertThrows(ParseException::class.java) {
            StatementList(statementListString)
        }
    }
}