import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class StatementListTest {

    @Test
    fun constructStatementList_empty_list() {
        var statementListString = ""
        var statementList = StatementList(statementListString)
        assertArrayEquals(mutableListOf<Statement>().toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_single_LET_statement() {
        var statementListString = "LET \$X = 5pm"
        var statementList = StatementList(statementListString)
        assertArrayEquals(mutableListOf<Statement>(
                LetStatement("LET \$X = 5pm")
        ).toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_single_FOR_statement() {
        var statementListString = "FOR \$X IN {5pm, 6pm} DO SCHEDULE dinner AT \$X ENDFOR"
        var statementList = StatementList(statementListString)
        assertArrayEquals(mutableListOf<Statement>(
                ForStatement("FOR \$X IN {5pm, 6pm} DO SCHEDULE dinner AT \$X ENDFOR")
        ).toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_single_SCHEDULE_statement() {
        var statementListString = "SCHEDULE dinner AT \$X"
        var statementList = StatementList(statementListString)
        var expectedStatements = mutableListOf(
                ScheduleStatement("SCHEDULE dinner AT \$X")
        ).toTypedArray();
        var actualStatements = statementList.statements.toTypedArray()
        //assertTrue(expectedStatements.contentEquals(actualStatements))
        assertArrayEquals(mutableListOf(
                ScheduleStatement("SCHEDULE dinner AT \$X")
        ).toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_multiple_statements() {
        var statementListString = "LET \$X = 5pm SCHEDULE dinner AT \$X FOR \$X IN {5pm, 6pm} DO SCHEDULE dinner AT \$X ENDFOR"
        var statementList = StatementList(statementListString)
        assertArrayEquals(mutableListOf<Statement>(
                LetStatement("LET \$X = 5pm"),
                ScheduleStatement("SCHEDULE dinner AT \$X"),
                ForStatement("FOR \$X IN {5pm, 6pm} DO SCHEDULE dinner AT \$X ENDFOR")
        ).toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_multiple_statements_wierd_spacing() {
        var statementListString = "   LET \$X =  5pm        SCHEDULE dinner AT     \$X     FOR     \$X IN {5pm, 6pm} DO SCHEDULE dinner AT \$X ENDFOR   "
        var statementList = StatementList(statementListString)
        assertArrayEquals(mutableListOf<Statement>(
                LetStatement("LET \$X = 5pm"),
                ScheduleStatement("SCHEDULE dinner AT \$X"),
                ForStatement("FOR \$X IN {5pm, 6pm} DO SCHEDULE dinner AT \$X ENDFOR")
        ).toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_nested_FOR_loops() {
        var statementListString = "FOR \$X IN {5pm, 6pm} DO FOR \$Y IN {dinner, lunch} DO SCHEDULE \$Y AT \$X ENDFOR ENDFOR"
        var statementList = StatementList(statementListString)
        assertArrayEquals(mutableListOf<Statement>(
                ForStatement("FOR \$X IN {5pm, 6pm} DO FOR \$Y IN {dinner, lunch} DO SCHEDULE \$Y AT \$X ENDFOR ENDFOR")
        ).toTypedArray(), statementList.statements.toTypedArray())
    }

    @Test
    fun constructStatementList_unmatched_FOR() {
        var statementListString = "FOR \$X IN {5pm, 6pm} DO SCHEDULE dinner AT \$X"
        val exception = assertThrows(ParseException::class.java) {
            StatementList(statementListString)
        }
    }

    @Test
    fun constructStatementList_unmatched_ENDFOR() {
        var statementListString = "FOR \$X IN {5pm, 6pm} DO SCHEDULE dinner AT \$X"
        val exception = assertThrows(ParseException::class.java) {
            StatementList(statementListString)
        }
    }
}