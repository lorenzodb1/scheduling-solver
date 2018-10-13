import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class ForStatementTest {

    // Test constructing a simple FOR loop with an empty Nodeset and no statements
    @Test
    fun constructForStatement_empty_nodeset_and_empty_statements() {
        val forString = "FOR \$X IN {} ENDFOR"

        val forStatement = ForStatement(forString);

        assertEquals(IdNode("\$X"), forStatement.id)
        assertEquals(NodeSet("{}"), forStatement.nodeSet)
        assertEquals(StatementList(""), forStatement.statements);
    }

    // Test constructing a simple FOR loop with an empty Nodeset and no statements
    @Test
    fun constructForStatement_empty_nodeset_and_empty_statements_wierd_spacing() {
        val forString = "   FOR    \$X    IN    {}      ENDFOR  "

        val forStatement = ForStatement(forString);

        assertEquals(IdNode("\$X"), forStatement.id)
        assertEquals(NodeSet("{}"), forStatement.nodeSet)
        assertEquals(StatementList(""), forStatement.statements);
    }

    // Test constructing a simple FOR loop with an empty Nodeset
    @Test
    fun constructForStatement_empty_nodeset() {
        val forString = "FOR \$X IN {} SCHEDULE dinner AT 7pm ENDFOR"

        val forStatement = ForStatement(forString);

        assertEquals(IdNode("\$X"), forStatement.id)
        assertEquals(NodeSet("{}"), forStatement.nodeSet)
        assertEquals(StatementList("SCHEDULE dinner AT 7pm"), forStatement.statements);
    }

    // Test constructing a simple FOR loop, where the id isn't used in the body
    @Test
    fun constructForStatement_single_iteration_id_not_used_in_body() {
        val forString = "FOR \$X IN {5pm} SCHEDULE dinner AT 7pm ENDFOR"

        val forStatement = ForStatement(forString);

        assertEquals(IdNode("\$X"), forStatement.id)
        assertEquals(NodeSet("{5pm}"), forStatement.nodeSet)
        assertEquals(StatementList("SCHEDULE dinner AT 7pm"), forStatement.statements);
    }

    // Test constructing a simple FOR loop, where the id isn't used in the body, with weird string spacing
    @Test
    fun constructForStatement_single_iteration_id_not_used_in_body_wierd_spacing() {
        val forString = "  FOR  \$X IN {5pm}      SCHEDULE dinner   AT        7pm   ENDFOR"

        val forStatement = ForStatement(forString);

        assertEquals(IdNode("\$X"), forStatement.id)
        assertEquals(NodeSet("{5pm}"), forStatement.nodeSet)
        assertEquals(StatementList("SCHEDULE dinner AT 7pm"), forStatement.statements);
    }

    // Test constructing a simple FOR loop, where the id is used in the body
    @Test
    fun constructForStatement_single_iteration_id_used_in_body() {
        val forString = "FOR \$X IN {5pm} SCHEDULE dinner AT \$X ENDFOR"

        val forStatement = ForStatement(forString);

        assertEquals(IdNode("\$X"), forStatement.id)
        assertEquals(NodeSet("{5pm}"), forStatement.nodeSet)
        assertEquals(StatementList("SCHEDULE dinner AT 5pm"), forStatement.statements);
    }

    // Test constructing a simple FOR loop, where the id replaces a description instead of a time
    @Test
    fun constructForStatement_single_iteration_id_replaces_description() {
        val forString = "FOR \$X IN {dinner} SCHEDULE \$X AT 7pm ENDFOR"

        val forStatement = ForStatement(forString)

        assertEquals(IdNode("\$X"), forStatement.id)
        assertEquals(NodeSet("{dinner}"), forStatement.nodeSet)
        assertEquals(StatementList("SCHEDULE \$X AT 7pm"), forStatement.statements)
    }

    // Test using variable name with more then one character
    @Test
    fun constructForStatement_multi_character_variable_name() {
        val forString = "FOR \$ABC IN {dinner} SCHEDULE \$ABC AT 7pm ENDFOR"

        val forStatement = ForStatement(forString)

        assertEquals(IdNode("\$ABC"), forStatement.id)
        assertEquals(NodeSet("{dinner}"), forStatement.nodeSet)
        assertEquals(StatementList("SCHEDULE \$ABC AT 7pm"), forStatement.statements);
    }

    // Test using variable name with lower and upper case characters
    @Test
    fun constructForStatement_mixed_case_variable_name() {
        val forString = "FOR \$abC IN {dinner} SCHEDULE \$abC AT 7pm ENDFOR"

        val forStatement = ForStatement(forString);

        assertEquals(IdNode("\$abC"), forStatement.id)
        assertEquals(NodeSet("{dinner}"), forStatement.nodeSet)
        assertEquals(StatementList("SCHEDULE \$abC AT 7pm"), forStatement.statements);
    }

    // Test constructing a simple FOR loop that runs for more then one iteration
    @Test
    fun constructForStatement_multiple_iterations() {
        val forString = "FOR \$X IN {5pm, 6pm} SCHEDULE dinner AT \$X ENDFOR"

        val forStatement = ForStatement(forString);

        assertEquals(IdNode("\$X"), forStatement.id)
        assertEquals(NodeSet("{5pm, 6pm}"), forStatement.nodeSet)
        assertEquals(StatementList("SCHEDULE \$X AT 5pm \$X"),
                forStatement.statements);
    }

    // Test constructing a nested FOR loop
    @Test
    fun constructForStatement_nested_loops() {
        val forString = "FOR \$X IN {5pm, 6pm} FOR \$Y IN {dinner, lunch} SCHEDULE dinner AT \$X ENDFOR ENDFOR"
        val forStatement = ForStatement(forString);

        assertEquals(IdNode("\$X"), forStatement.id)
        assertEquals(NodeSet("{5pm, 6pm}"), forStatement.nodeSet)
        assertEquals(StatementList("FOR \$Y IN {dinner, lunch} SCHEDULE dinner AT \$Y ENDFOR"),
                forStatement.statements);
    }

    // Test constructing a nested FOR loop with id shadowing
    @Test
    fun constructForStatement_nested_loops_id_shadowed() {
        val forString = "FOR \$X IN {5pm, 6pm} FOR \$X IN {3pm, 4pm} SCHEDULE dinner AT \$X ENDFOR ENDFOR"
        val forStatement = ForStatement(forString);

        assertEquals(IdNode("\$X"), forStatement.id)
        assertEquals(NodeSet("{5pm, 6pm}"), forStatement.nodeSet)
        assertEquals(StatementList("FOR \$X IN {3pm, 4pm} SCHEDULE dinner AT \$X ENDFOR"),
                forStatement.statements);
    }

    @Test
    fun constructForStatement_missing_ENDFOR() {
        val forString = "FOR \$X IN {5pm} SCHEDULE dinner AT \$X"

        val exception = assertThrows(ParseException::class.java) {
            ForStatement(forString)
        }
    }

    @Test
    fun constructForStatement_missing_FOR() {
        val forString = "\$X IN {5pm} SCHEDULE dinner AT \$X ENDFOR"

        val exception = assertThrows(ParseException::class.java) {
            ForStatement(forString)
        }
    }

    @Test
    fun constructForStatement_missing_Id() {
        val forString = "FOR IN {5pm} SCHEDULE dinner AT \$X ENDFOR"

        val exception = assertThrows(ParseException::class.java) {
            ForStatement(forString)
        }
    }

    @Test
    fun constructForStatement_missing_nodeset() {
        val forString = "FOR \$X IN SCHEDULE dinner AT \$X ENDFOR"

        val exception = assertThrows(ParseException::class.java) {
            ForStatement(forString)
        }
    }

    // TODO: test nested nodesets
}