import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import java.util.*


@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class DayNodeTest {

    @Test
    fun constructDayNode_empty_string() {
        val dayString = ""
        val exception = assertThrows(ParseException::class.java) {
            DayNode(dayString)
        }
    }

    @Test
    fun constructDayNode_garbage_string() {
        val dayString = "monasday"
        val exception = assertThrows(ParseException::class.java) {
            DayNode(dayString)
        }
    }

    @Test
    fun constructDayNode_lower_case_monday() {
        val dayString = "monday"
        var dayNode = DayNode(dayString)
        assertEquals(Calendar.MONDAY, dayNode.day)
    }

    @Test
    fun constructDayNode_wierd_spacing() {
        val dayString = "   monday   "
        var dayNode = DayNode(dayString)
        assertEquals(Calendar.MONDAY, dayNode.day)
    }

    @Test
    fun constructDayNode_lower_case_monday_abbreviated() {
        val dayString = "mon"
        var dayNode = DayNode(dayString)
        assertEquals(Calendar.MONDAY, dayNode.day)
    }

    @Test
    fun constructDayNode_upper_case_monday() {
        val dayString = "Monday"
        var dayNode = DayNode(dayString)
        assertEquals(Calendar.MONDAY, dayNode.day)
    }

    @Test
    fun constructDayNode_lower_case_wednesday() {
        val dayString = "wednesday"
        var dayNode = DayNode(dayString)
        assertEquals(Calendar.WEDNESDAY, dayNode.day)
    }

}