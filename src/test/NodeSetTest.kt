import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class NodeSetTest {

    // Test constructing a simple FOR loop with an empty Nodeset and no statements
//    @Test
//    fun constructForStatement_empty_nodeset_and_empty_statements() {
//        var forString = "FOR \$X IN {} ENDFOR\n"
//
//        var forStatement = ForStatement(forString);
//
//        assertArrayEquals(IdNode("\$X"), forStatement.id)
//        assertArrayEquals(NodeSet("{}"), forStatement.nodeSet)
//        assertArrayEquals(StatementList(""), forStatement.statements);
//    }

    @Test
    fun constructNodeSet_empty_set() {
        var nodeSetString = "{}"
        var nodeSet = NodeSet(nodeSetString);
        assertArrayEquals(arrayOf<String>(), nodeSet.nodes)
    }

    @Test
    fun constructNodeSet_single_element() {
        var nodeSetString = "{January}"
        var nodeSet = NodeSet(nodeSetString);
        assertArrayEquals(arrayOf<String>("January"), nodeSet.nodes)
    }

    @Test
    fun constructNodeSet_multiple_elements() {
        var nodeSetString = "{January, 5pm, asdfasdf}"
        var nodeSet = NodeSet(nodeSetString);
        assertArrayEquals(arrayOf<String>("January", "5pm", "asdfasdf"), nodeSet.nodes)
    }

    @Test
    fun constructNodeSet_multiple_elements_empty_element_in_middle() {
        var nodeSetString = "{January, , asdfasdf}"
        var nodeSet = NodeSet(nodeSetString);
        assertArrayEquals(arrayOf<String>("January", "", "asdfasdf"), nodeSet.nodes)
    }

    @Test
    fun constructNodeSet_multiple_elements_empty_element_at_end() {
        var nodeSetString = "{January, 5pm, }"
        var nodeSet = NodeSet(nodeSetString);
        assertArrayEquals(arrayOf<String>("January", "5pm", ""), nodeSet.nodes)
    }

    @Test
    fun constructNodeSet_multiple_elements_random_spacing() {
        var nodeSetString = "{ January ,    5pm, asdfasdf}"
        var nodeSet = NodeSet(nodeSetString);
        assertArrayEquals(arrayOf<String>("January", "5pm", "asdfasdf"), nodeSet.nodes)
    }

    @Test
    fun constructNodeSet_multiple_elements_spaces_in_elements() {
        var nodeSetString = "{ dinner with timmy ,  hello world , asdfasdf}"
        var nodeSet = NodeSet(nodeSetString);
        assertArrayEquals(arrayOf<String>("dinner with timmy", "hello world", "asdfasdf"), nodeSet.nodes)
    }

    @Test
    fun constructNodeSet_no_elements_missing_first_bracket() {
        var nodeSetString = "}"
        val exception = assertThrows(ParseException::class.java) {
            NodeSet(nodeSetString);
        }
    }

    @Test
    fun constructNodeSet_no_elements_missing_second_bracket() {
        var nodeSetString = "{"
        val exception = assertThrows(ParseException::class.java) {
            NodeSet(nodeSetString);
        }
    }

    @Test
    fun constructNodeSet_some_elements_missing_first_bracket() {
        var nodeSetString = "a, b, c}"
        val exception = assertThrows(ParseException::class.java) {
            NodeSet(nodeSetString);
        }
    }

    @Test
    fun constructNodeSet_some_elements_missing_second_bracket() {
        var nodeSetString = "{a, b, c,"
        val exception = assertThrows(ParseException::class.java) {
            NodeSet(nodeSetString);
        }
    }
}