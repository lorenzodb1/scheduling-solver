import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance



@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class GuestNodeTest {

    @Test
    fun guestNode_empty_string() {
        val guestString = ""
        val exception = assertThrows(ParseException::class.java) {
            GuestNode(guestString)
        }
    }

    @Test
    fun constructGuestNode_garbage_string() {
        val guestString = "asdas"
        val exception = assertThrows(ParseException::class.java) {
            GuestNode(guestString)
        }
    }

    @Test
    fun constructGuestNode_missing_name() {
        val guestString = "@gmail.com"
        val exception = assertThrows(ParseException::class.java) {
            GuestNode(guestString)
        }
    }

    @Test
    fun constructGuestNode_missing_at() {
        val guestString = "asdgmail.com"
        val exception = assertThrows(ParseException::class.java) {
            GuestNode(guestString)
        }
    }

    @Test
    fun constructGuestNode_at_replaced_with_invalid_character() {
        val guestString = "asd%gmail.com"
        val exception = assertThrows(ParseException::class.java) {
            GuestNode(guestString)
        }
    }

    @Test
    fun constructGuestNode_missing_url() {
        val guestString = "asd@"
        val exception = assertThrows(ParseException::class.java) {
            GuestNode(guestString)
        }
    }

    @Test
    fun constructGuestNode_simple_email() {
        val guestString = "asdf@tees.edu"
        val guestNode = GuestNode(guestString)
        assertEquals(guestString, guestNode.email)
    }
}