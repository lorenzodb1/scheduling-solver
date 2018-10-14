import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class LetStatementTest {

    @Test
    fun constructLetStatement_single_letter_id_uppercase() {
        val letString = "LET \$X = test"
        val letStatement = LetStatement(letString);
        assertEquals(IdNode("\$X"), letStatement.id)
        assertEquals("test", letStatement.value)
    }

    @Test
    fun constructLetStatement_wierd_spacing() {
        val letString = "   LET    \$X    =    test   "
        val letStatement = LetStatement(letString);
        assertEquals(IdNode("\$X"), letStatement.id)
        assertEquals("test", letStatement.value)
    }

    @Test
    fun constructLetStatement_single_letter_id_lowercase() {
        val letString = "LET \$x = test"
        val letStatement = LetStatement(letString);
        assertEquals(IdNode("\$x"), letStatement.id)
        assertEquals("test", letStatement.value)
    }

    @Test
    fun constructLetStatement_missing_LET() {
        val letString =  "\$X = test"
        assertThrows(ParseException::class.java) {
            LetStatement(letString)
        }
    }

    @Test
    fun constructLetStatement_misspelled_LET() {
        val letString =  "LER \$X = test"
        assertThrows(ParseException::class.java) {
            LetStatement(letString)
        }
    }

    @Test
    fun constructLetStatement_missing_equals_sign() {
        val letString =  "LER \$X test"
        assertThrows(ParseException::class.java) {
            LetStatement(letString)
        }
    }

    @Test
    fun constructLetStatement_missing_value() {
        val letString =  "LER \$X =  "
        assertThrows(ParseException::class.java) {
            LetStatement(letString)
        }
    }

    @Test
    fun constructLetStatement_illegal_character_in_value() {
        val letString =  "LET \$X = \$Y"
        assertThrows(ParseException::class.java) {
            LetStatement(letString)
        }
    }

    @Test
    fun constructLetStatement_empty_string() {
        val dayString = ""
        assertThrows(ParseException::class.java) {
            DayNode(dayString)
        }
    }

}