import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class IdNodeTest {

    @Test
    fun constructIdNode_single_character_uppercase() {
        var idString = "\$X"
        var idNode = IdNode(idString)
        assertEquals("X", idNode.id)
    }

    @Test
    fun constructIdNode_single_character_lowercase() {
        var idString = "\$x"
        var idNode = IdNode(idString)
        assertEquals("x", idNode.id)
    }

    @Test
    fun constructIdNode_multiple_character_uppercase() {
        var idString = "\$XYZ"
        var idNode = IdNode(idString)
        assertEquals("XYZ", idNode.id)
    }

    @Test
    fun constructIdNode_multiple_character_lowercase() {
        var idString = "\$xyz"
        var idNode = IdNode(idString)
        assertEquals("xyz", idNode.id)
    }

    @Test
    fun constructIdNode_single_character_with_spaces_before_and_after() {
        var idString = "    \$X "
        var idNode = IdNode(idString)
        assertEquals("X", idNode.id)
    }

    @Test
    fun constructIdNode_missing_dollar_sign() {
        var idString = "x"
        val exception = assertThrows(ParseException::class.java) {
            IdNode(idString)
        }
    }

    @Test
    fun constructIdNode_spaces_in_id() {
        var idString = "\$X Y"
        val exception = assertThrows(ParseException::class.java) {
            IdNode(idString)
        }
    }

    @Test
    fun constructIdNode_missing_id() {
        var idString = "$"
        val exception = assertThrows(ParseException::class.java) {
            IdNode(idString)
        }
    }
}