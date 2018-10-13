import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class DurationNodeTest {

    @Test
    fun constructDurationNode_empty_string() {
        val durationString = ""
        val exception = assertThrows(ParseException::class.java) {
            DateNode(durationString)
        }
    }

    @Test
    fun constructDurationNode_missing_value() {
        val durationString = "hours"
        val exception = assertThrows(ParseException::class.java) {
            DateNode(durationString)
        }
    }

    @Test
    fun constructDurationNode_missing_unit() {
        val durationString = "3"
        val exception = assertThrows(ParseException::class.java) {
            DateNode(durationString)
        }
    }

    @Test
    fun constructDurationNode_single_digit_minutes() {
        val durationString = "3 minutes"
        var durationNode = DurationNode(durationString)
        assertEquals(3, durationNode.minutes)
    }

    @Test
    fun constructDurationNode_multiple_digits_minutes() {
        val durationString = "30 minutes"
        var durationNode = DurationNode(durationString)
        assertEquals(30, durationNode.minutes)
    }

    @Test
    fun constructDurationNode_multiple_digits_hours() {
        val durationString = "30 hours"
        var durationNode = DurationNode(durationString)
        assertEquals(30*60, durationNode.minutes)
    }

    @Test
    fun constructDurationNode_wierd_spacing() {
        val durationString = "   3    minutes   "
        var durationNode = DurationNode(durationString)
        assertEquals(3, durationNode.minutes)
    }

}