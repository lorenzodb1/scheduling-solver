import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance



@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class MonthNodeTest {

    @Test
    fun constructMonthNode_empty_string() {
        val monthString = ""
        val exception = assertThrows(ParseException::class.java) {
            MonthNode(monthString)
        }
    }

    @Test
    fun constructIdNode_lower_case_january() {
        val monthString = "january"
        var monthNode = MonthNode(monthString)
        assertEquals(MonthNode.Companion.Month.JANUARY, monthNode.month)
    }

    @Test
    fun constructIdNode_lower_case_january_abbreviated() {
        val monthString = "jan"
        var monthNode = MonthNode(monthString)
        assertEquals(MonthNode.Companion.Month.JANUARY, monthNode.month)
    }

    @Test
    fun constructIdNode_upper_case_january() {
        val monthString = "January"
        var monthNode = MonthNode(monthString)
        assertEquals(MonthNode.Companion.Month.JANUARY, monthNode.month)
    }

    @Test
    fun constructIdNode_lower_case_february() {
        val monthString = "february"
        var monthNode = MonthNode(monthString)
        assertEquals(MonthNode.Companion.Month.FEBRUARY, monthNode.month)
    }

}