import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class TimeNodeTest {

    @Test
    fun constructTimeNode_empty_string() {
        val timeString = ""
        val exception = assertThrows(ParseException::class.java) {
            TimeNode(timeString)
        }
    }

    @Test
    fun constructTimeNode_am_hour_0_minutes() {
        val timeString = "5am"
        val timeNode = TimeNode(timeString)
        assertEquals(5, timeNode.hour)
        assertEquals(0, timeNode.minute)
    }

    @Test
    fun constructTimeNode_pm_hour_0_minutes() {
        val timeString = "5pm"
        val timeNode = TimeNode(timeString)
        assertEquals(17, timeNode.hour)
        assertEquals(0, timeNode.minute)
    }

    @Test
    fun constructTimeNode_am_hour_uppercase() {
        val timeString = "5AM"
        val timeNode = TimeNode(timeString)
        assertEquals(5, timeNode.hour)
        assertEquals(0, timeNode.minute)
    }

    @Test
    fun constructTimeNode_pm_hour_uppercase() {
        val timeString = "5PM"
        val timeNode = TimeNode(timeString)
        assertEquals(17, timeNode.hour)
        assertEquals(0, timeNode.minute)
    }

    @Test
    fun constructTimeNode_pm_hour_20_minutes() {
        val timeString = "5:20pm"
        val timeNode = TimeNode(timeString)
        assertEquals(17, timeNode.hour)
        assertEquals(20, timeNode.minute)
    }
    @Test
    fun constructTimeNode_space_before_pm() {
        val timeString = "5:20 pm"
        val timeNode = TimeNode(timeString)
        assertEquals(17, timeNode.hour)
        assertEquals(20, timeNode.minute)
    }

    @Test
    fun constructTimeNode_no_am_or_pm() {
        val timeString = "5:20"
        val timeNode = TimeNode(timeString)
        assertEquals(5, timeNode.hour)
        assertEquals(20, timeNode.minute)
    }

    @Test
    fun constructTimeNode_multiple_numbers_in_hour_and_minute() {
        val timeString = "11:29"
        val timeNode = TimeNode(timeString)
        assertEquals(11, timeNode.hour)
        assertEquals(29, timeNode.minute)
    }

    @Test
    fun constructTimeNode_24_hour_time_with_pm() {
        val timeString = "13:00pm"
        val exception = assertThrows(ParseException::class.java) {
            TimeNode(timeString)
        }
    }

    @Test
    fun constructTimeNode_24_hour_time_with_am() {
        val timeString = "13:00am"
        val exception = assertThrows(ParseException::class.java) {
            TimeNode(timeString)
        }
    }

    @Test
    fun constructTimeNode_24_hour_time() {
        val timeString = "15:00"
        val timeNode = TimeNode(timeString)
        assertEquals(15, timeNode.hour)
        assertEquals(0, timeNode.minute)
    }

    @Test
    fun constructTimeNode_morning_no_am_or_pm() {
        val timeString = "8:00"
        val timeNode = TimeNode(timeString)
        assertEquals(8, timeNode.hour)
        assertEquals(0, timeNode.minute)
    }

    @Test
    fun constructTimeNode_hour_no_minutes() {
        val timeString = "8"
        val timeNode = TimeNode(timeString)
        assertEquals(8, timeNode.hour)
        assertEquals(0, timeNode.minute)
    }
}