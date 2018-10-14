import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance


@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class DateNodeTest {

    @Test
    fun constructDateNode_empty_string() {
        val dateString = ""
        val exception = assertThrows(ParseException::class.java) {
            DateNode(dateString)
        }
    }

    @Test
    fun constructDateNode_negative_date() {
        val dateString = "-2 of January"
        val exception = assertThrows(ParseException::class.java) {
            DateNode(dateString)
        }
    }

    @Test
    fun constructDateNode_date_single_number_no_suffix_month_suffix() {
        val dateString = "2 of January"
        var dateNode = DateNode(dateString)
        assertEquals(MonthNode("January"), dateNode.month)
        assertEquals(1, dateNode.dayOfMonth)
    }

    @Test
    fun constructDateNode_date_multiple_numbers_no_suffix_month_suffix() {
        val dateString = "22 of January"
        var dateNode = DateNode(dateString)
        assertEquals(MonthNode("January"), dateNode.month)
        assertEquals(21, dateNode.dayOfMonth)
    }

    @Test
    fun constructDateNode_date_nd_suffix_month_suffix() {
        val dateString = "2nd of January"
        var dateNode = DateNode(dateString)
        assertEquals(MonthNode("January"), dateNode.month)
        assertEquals(1, dateNode.dayOfMonth)
    }

    @Test
    fun constructDateNode_date_st_suffix_month_suffix() {
        val dateString = "21st of January"
        var dateNode = DateNode(dateString)
        assertEquals(MonthNode("January"), dateNode.month)
        assertEquals(20, dateNode.dayOfMonth)
    }

    @Test
    fun constructDateNode_different_month_month_suffix() {
        val dateString = "2 of April"
        var dateNode = DateNode(dateString)
        assertEquals(MonthNode("April"), dateNode.month)
        assertEquals(1, dateNode.dayOfMonth)
    }

    @Test
    fun constructDateNode_date_single_number_no_suffix_month_prefix() {
        val dateString = "January 2"
        var dateNode = DateNode(dateString)
        assertEquals(MonthNode("January"), dateNode.month)
        assertEquals(1, dateNode.dayOfMonth)
    }

    @Test
    fun constructDateNode_date_multiple_numbers_no_suffix_month_prefix() {
        val dateString = "January 22"
        var dateNode = DateNode(dateString)
        assertEquals(MonthNode("January"), dateNode.month)
        assertEquals(21, dateNode.dayOfMonth)
    }

    @Test
    fun constructDateNode_date_nd_suffix_month_prefix() {
        val dateString = "January 2nd"
        var dateNode = DateNode(dateString)
        assertEquals(MonthNode("January"), dateNode.month)
        assertEquals(1, dateNode.dayOfMonth)
    }

    @Test
    fun constructDateNode_date_st_suffix_month_prefix() {
        val dateString = "January 21st"
        var dateNode = DateNode(dateString)
        assertEquals(MonthNode("January"), dateNode.month)
        assertEquals(20, dateNode.dayOfMonth)
    }

    @Test
    fun constructDateNode_different_month_month_prefix() {
        val dateString = "April 2"
        var dateNode = DateNode(dateString)
        assertEquals(MonthNode("April"), dateNode.month)
        assertEquals(1, dateNode.dayOfMonth)
    }

}