import org.junit.jupiter.api.TestInstance

import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class ScheduleStatementTest {

    @BeforeEach
    fun beforeEach() {
        // Set the date to a static value for testing purposes
        CurrentDate.date = java.util.Date(2018, 10, 13)
    }

    // We only have one test here because we're delaying all the interesting parsing work until
    // evaluation time after we've substituted in all the variables
    @Test
    fun constructScheduleStatement() {
        val scheduleString = "SCHEDULE Fencing AT 5 am FOR 2 hours IN UBC ON EVERY Sunday AND Monday UNTIL October 17th WITH leticia.c.nakajima@gmail.com"
        val scheduleStatement = ScheduleStatement(scheduleString)
        assertEquals(scheduleString, scheduleStatement.savedScheduleString)
    }

    @Test
    fun substituteVariables_no_variables_wierd_spacing(){
        val symbolTable = SymbolTable()
        val str = "This is some text,    SCHEDULE       Fencing AT 5 am"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals(str, substitutedStr)
    }

    @Test
    fun substituteVariables_one_variable_nothing_else(){
        val symbolTable = SymbolTable()
        symbolTable.set("X", "hello world!")
        val str = "\$X"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals("hello world!", substitutedStr)
    }

    @Test
    fun substituteVariables_two_same_variables_nothing_else(){
        val symbolTable = SymbolTable()
        symbolTable.set("X", "hello world! ")
        val str = "\$X\$X"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals("hello world! hello world! ", substitutedStr)
    }

    @Test
    fun substituteVariables_unused_variables_in_symbol_table(){
        val symbolTable = SymbolTable()
        symbolTable.set("X", "hello world!")
        symbolTable.set("Z", "unused!")
        val str = "\$X"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals("hello world!", substitutedStr)
    }

    @Test
    fun substituteVariables_two_different_variables_nothing_else(){
        val symbolTable = SymbolTable()
        symbolTable.set("X", "hello world! ")
        symbolTable.set("Y", "SCHEDULE")
        val str = "\$X\$Y"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals("hello world! SCHEDULE", substitutedStr)
    }

    @Test
    fun substituteVariables_one_variable_in_text(){
        val symbolTable = SymbolTable()
        symbolTable.set("Y", "Sleeper Service")
        val str = "GSV - \$Y"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals("GSV - Sleeper Service", substitutedStr)
    }

    @Test
    fun substituteVariables_multiple_variables_in_text(){
        val symbolTable = SymbolTable()
        symbolTable.set("X", "5pm")
        symbolTable.set("Y", "dinner")
        val str = "SCHEDULE \$Y AT \$X"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals("SCHEDULE dinner AT 5pm", substitutedStr)
    }

    @Test
    fun substituteVariables_one_variable_not_in_symbol_table(){
        val symbolTable = SymbolTable()
        symbolTable.set("Y", "dinner")
        val str = "SCHEDULE \$Z AT 5pm"
        val exception = assertThrows(InterpException::class.java) {
            ScheduleStatement.subsituteVariables(str, symbolTable)
        }
    }

    @Test
    fun substituteVariables_multiple_variables_one_not_in_symbol_table(){
        val symbolTable = SymbolTable()
        symbolTable.set("Y", "dinner")
        val str = "SCHEDULE \$Y AT \$X"
        val exception = assertThrows(InterpException::class.java) {
            ScheduleStatement.subsituteVariables(str, symbolTable)
        }
    }

    @Test
    fun substituteVariables_multiple_variables_all_not_in_symbol_table(){
        val symbolTable = SymbolTable()
        symbolTable.set("Z", "dinner")
        val str = "SCHEDULE \$Y AT \$X"
        val exception = assertThrows(InterpException::class.java) {
            ScheduleStatement.subsituteVariables(str, symbolTable)
        }
    }

    @Test
    fun splitOnAndKeepWords_simple() {
        assertArrayEquals(
                arrayOf("A B C", "D E", "F G"),
                ScheduleStatement.splitOnAndKeepWords("A B C D E F G", arrayOf("A", "D", "F"))
        )
    }
    @Test
    fun splitOnAndKeepWords_no_splitvals() {
        assertArrayEquals(
                arrayOf("A B C D E F G"),
                ScheduleStatement.splitOnAndKeepWords("A B C D E F G", arrayOf())
        )
    }

    @Test
    fun interp_missing_SCHEDULE_keyword() {
        val scheduleString = "dinner AT 5pm"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        val exception = assertThrows(InterpException::class.java) {
            scheduleStatement.interp(symbolTable)
        }
    }

    @Test
    fun interp_missing_description() {
        val scheduleString = "SCHEDULE AT 5pm"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        val exception = assertThrows(InterpException::class.java) {
            scheduleStatement.interp(symbolTable)
        }
    }

    @Test
    fun interp_missing_entire_AT_expression() {
        val scheduleString = "SCHEDULE dinner"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        val exception = assertThrows(InterpException::class.java) {
            scheduleStatement.interp(symbolTable)
        }
    }

    @Test
    fun interp_missing_AT_value() {
        val scheduleString = "SCHEDULE dinner AT"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        val exception = assertThrows(ParseException::class.java) {
            scheduleStatement.interp(symbolTable)
        }
    }

    @Test
    fun interp_description_and_time() {
        val scheduleString = "SCHEDULE dinner AT 5pm"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(null, scheduleStatement.duration)
        assertEquals(null, scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(), scheduleStatement.guests)
    }

    @Test
    fun interp_description_time_and_duration() {
        val scheduleString = "SCHEDULE dinner AT 5pm FOR 2 hours"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
        assertEquals(null, scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(), scheduleStatement.guests)
    }

    @Test
    fun interp_description_and_time_location() {
        val scheduleString = "SCHEDULE dinner AT 5pm IN thunderbird field"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(null, scheduleStatement.duration)
        assertEquals(LocationNode("thunderbird field"), scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(), scheduleStatement.guests)
    }

    @Test
    fun interp_description_and_time_and_date() {
        val scheduleString = "SCHEDULE dinner AT 5pm ON April 5th"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(null, scheduleStatement.duration)
        assertEquals(null, scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(
                Date(2019, 3, 4)
        ), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(), scheduleStatement.guests)
    }

    @Test
    fun interp_description_and_time_and_specific_dates() {
        val scheduleString = "SCHEDULE dinner AT 5pm ON April 5th AND March 3rd AND December 20th"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(null, scheduleStatement.duration)
        assertEquals(null, scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(
                Date(2018, 11, 19),
                Date(2019, 2, 2),
                Date(2019, 3, 4)
        ), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(), scheduleStatement.guests)
    }

    @Test
    fun interp_description_and_time_and_repeat_dates() {
        val scheduleString = "SCHEDULE dinner AT 5pm ON EVERY Tuesday UNTIL December 1st"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(null, scheduleStatement.duration)
        assertEquals(null, scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(
                Date(2018, 10, 19),
                Date(2018, 10, 26)
        ), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(), scheduleStatement.guests)
    }

    @Test
    fun interp_description_and_time_and_repeat_dates_multiple_weekdays() {
        val scheduleString = "SCHEDULE dinner AT 5pm ON EVERY Tuesday EVERY Sunday AND Monday UNTIL October November 1st"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(null, scheduleStatement.duration)
        assertEquals(null, scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(
                Date(2018, 10, 14),
                Date(2018, 10, 15),
                Date(2018, 10, 21),
                Date(2018, 10, 22),
                Date(2018, 10, 28),
                Date(2018, 10, 29)
                ), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(), scheduleStatement.guests)
    }

    @Test
    fun interp_description_and_time_and_single_guest() {
        val scheduleString = "SCHEDULE dinner AT 5pm WITH asdf@asdf.com"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(null, scheduleStatement.duration)
        assertEquals(null, scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(
                GuestNode("asdf@asdf.com")
        ), scheduleStatement.guests)
    }

    @Test
    fun interp_description_and_time_and_multiple_guests() {
        val scheduleString = "SCHEDULE dinner AT 5pm WITH asdf@asdf.com AND l33thAx0r@russia.ru"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(null, scheduleStatement.duration)
        assertEquals(null, scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(
                GuestNode("asdf@asdf.com"),
                GuestNode("l33thAx0r@russia.ru")
        ), scheduleStatement.guests)
    }

    @Test
    fun interp_description_time_and_duration_and_location() {
        val scheduleString = "SCHEDULE dinner AT 5pm FOR 23 hours IN Huge Dumpster"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(DurationNode("23 hours"), scheduleStatement.duration)
        assertEquals(LocationNode("Huge Dumpster"), scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(), scheduleStatement.guests)
    }

    @Test
    fun interp_description_time_and_location_and_duration() {
        val scheduleString = "SCHEDULE dinner AT 5pm IN Huge Dumpster FOR 2 hours"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
        assertEquals(LocationNode("Huge Dumpster"), scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(), scheduleStatement.guests)
    }

    @Test
    fun interp_giant_example_1() {
        val scheduleString = "SCHEDULE Fencing AT 5 am FOR 2 hours IN UBC ON EVERY Tuesday AND Wednesday UNTIL December 5th WITH leticia.c.nakajima@gmail.com"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        scheduleStatement.interp(symbolTable)

        assertEquals("Fencing", scheduleStatement.description)
        assertEquals(TimeNode("5 am"), scheduleStatement.time)
        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
        assertEquals(LocationNode("UBC"), scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(
                Date(2018, 10, 13),
                Date(2018, 10, 19),
                Date(2018, 10, 20),
                Date(2018, 10, 26),
                Date(2018, 10, 27),
                Date(2018, 11, 3),
                Date(2018, 11, 4)
        ), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(
                GuestNode("leticia.c.nakajima@gmail.com")
        ), scheduleStatement.guests)
    }

    @Test
    fun interp_description_and_time_single_variable() {
        val scheduleString = "SCHEDULE dinner AT \$X"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        symbolTable.set("X", "5pm")
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(null, scheduleStatement.duration)
        assertEquals(null, scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(), scheduleStatement.guests)
    }

    @Test
    fun interp_description_and_time_multiple_variables() {
        val scheduleString = "SCHEDULE \$Y AT \$X"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        symbolTable.set("X", "5pm")
        symbolTable.set("Y", "dinner")
        scheduleStatement.interp(symbolTable)

        assertEquals("dinner", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(null, scheduleStatement.duration)
        assertEquals(null, scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(), scheduleStatement.guests)
    }

    @Test
    fun interp_description_and_time_variable_with_spaces() {
        val scheduleString = "SCHEDULE \$Y AT 5pm"
        val scheduleStatement = ScheduleStatement(scheduleString)
        val symbolTable = SymbolTable()
        symbolTable.set("Y", "a trip with GSV Little Rascal")
        scheduleStatement.interp(symbolTable)

        assertEquals("a trip with GSV Little Rascal", scheduleStatement.description)
        assertEquals(TimeNode("5pm"), scheduleStatement.time)
        assertEquals(null, scheduleStatement.duration)
        assertEquals(null, scheduleStatement.location)
        assertArrayEquals(arrayOf<java.util.Date>(), scheduleStatement.dates)
        assertArrayEquals(arrayOf<GuestNode>(), scheduleStatement.guests)
    }

    // TODO: It would be good to fixup and use some of these larger tests, but not enough time right now.......
//    @Test
//    fun basic_example_with_all_elements() {
//        val scheduleString = "SCHEDULE Fencing AT 5 am FOR 2 hours IN UBC ON EVERY Sunday AND Monday UNTIL October 17th WITH leticia.c.nakajima@gmail.com"
//
//        val scheduleStatement = ScheduleStatement(scheduleString)
//
//        assertEquals(("Fencing"), scheduleStatement.description)
//        assertEquals(TimeNode("5 am"), scheduleStatement.time)
//        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
//        assertEquals(LocationNode("UBC"), scheduleStatement.location)
//        assertEquals(DateNode("october 14th, october 15th"), scheduleStatement.dates)
//        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), scheduleStatement.guests)
//
//
//    }
//
//        @Test
//        fun time_written_a_little_differently() {
//            val scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"
//
//            val scheduleStatement = ScheduleStatement(scheduleString)
//
//            assertEquals(("Fencing"), scheduleStatement.description)
//            assertEquals(TimeNode("5 AM"), scheduleStatement.time)
//            assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
//            assertEquals(LocationNode("UBC"), scheduleStatement.location)
//            assertEquals(DateNode("october 14th, october 15th"), scheduleStatement.dates)
//            assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), scheduleStatement.guests)
//
//        }
//
//    @Test
//    fun no_duration_node() {
//        val scheduleString= "SCHEDULE Fencing AT 5 AM IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"
//
//        val scheduleStatement = ScheduleStatement(scheduleString)
//
//        assertEquals(("Fencing"), scheduleStatement.description)
//        assertEquals(TimeNode("5 AM"), scheduleStatement.time)
//        assertEquals(LocationNode("UBC"), scheduleStatement.location)
//        assertEquals(DateNode("october 14th, october 15th"), scheduleStatement.dates)
//        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), scheduleStatement.guests)
//    }
//
//    @Test
//    fun no_location_node() {
//        val scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"
//
//        val scheduleStatement = ScheduleStatement(scheduleString)
//
//        assertEquals(("Fencing"), scheduleStatement.description)
//        assertEquals(TimeNode("5 AM"), scheduleStatement.time)
//        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
//        assertEquals(DateNode("october 14th, october 15th"), scheduleStatement.dates)
//        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), scheduleStatement.guests)
//    }
//
//    @Test
//    fun no_dates_node() {
//        val scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC WITH leticia.c.nakajima@gmail.com"
//
//        val scheduleStatement = ScheduleStatement(scheduleString)
//
//        assertEquals(("Fencing"), scheduleStatement.description)
//        assertEquals(TimeNode("5 AM"), scheduleStatement.time)
//        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
//        assertEquals(LocationNode("UBC"), scheduleStatement.location)
//        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), scheduleStatement.guests)
//    }
//
//    @Test
//    fun no_guest_node() {
//        val scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November"
//
//        val scheduleStatement = ScheduleStatement(scheduleString)
//        assertEquals(("Fencing"), scheduleStatement.description)
//        assertEquals(TimeNode("5 AM"), scheduleStatement.time)
//        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
//        assertEquals(LocationNode("UBC"), scheduleStatement.location)
//        assertEquals(DateNode("october 14th, october 15th"), scheduleStatement.dates)
//    }

    @Test
    fun basic_example_with_all_elements() {
        val scheduleString = "SCHEDULE Fencing AT 5 am FOR 2 hours IN UBC ON EVERY Sunday AND Monday UNTIL October 17th WITH leticia.c.nakajima@gmail.com"
        val scheduleStatement = ScheduleStatement(scheduleString)

        assertEquals(("Fencing"), scheduleStatement.description)
        assertEquals(TimeNode("5 am"), scheduleStatement.time)
        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
        assertEquals(LocationNode("UBC"), scheduleStatement.location)
        assertEquals(DateNode("october 14th, october 15th"), scheduleStatement.dates)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), scheduleStatement.guests)
    }

    @Test
    fun time_written_a_little_differently() {
        val scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"

        val scheduleStatement = ScheduleStatement(scheduleString)

        assertEquals(("Fencing"), scheduleStatement.description)
        assertEquals(TimeNode("5 AM"), scheduleStatement.time)
        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
        assertEquals(LocationNode("UBC"), scheduleStatement.location)
        assertEquals(DateNode("october 14th, october 15th"), scheduleStatement.dates)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), scheduleStatement.guests)

    }

    @Test
    fun no_duration_node() {
        val scheduleString= "SCHEDULE Fencing AT 5 AM IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"

        val scheduleStatement = ScheduleStatement(scheduleString)

        assertEquals(("Fencing"), scheduleStatement.description)
        assertEquals(TimeNode("5 AM"), scheduleStatement.time)
        assertEquals(LocationNode("UBC"), scheduleStatement.location)
        assertEquals(DateNode("october 14th, october 15th"), scheduleStatement.dates)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), scheduleStatement.guests)
    }

    @Test
    fun no_location_node() {
        val scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"

        val scheduleStatement = ScheduleStatement(scheduleString)

        assertEquals(("Fencing"), scheduleStatement.description)
        assertEquals(TimeNode("5 AM"), scheduleStatement.time)
        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
        assertEquals(DateNode("october 14th, october 15th"), scheduleStatement.dates)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), scheduleStatement.guests)
    }

    @Test
    fun no_dates_node() {
        val scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC WITH leticia.c.nakajima@gmail.com"

        val scheduleStatement = ScheduleStatement(scheduleString)

        assertEquals(("Fencing"), scheduleStatement.description)
        assertEquals(TimeNode("5 AM"), scheduleStatement.time)
        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
        assertEquals(LocationNode("UBC"), scheduleStatement.location)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), scheduleStatement.guests)
    }

    @Test
    fun no_guest_node() {
        val scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November"

        val scheduleStatement = ScheduleStatement(scheduleString)
        assertEquals(("Fencing"), scheduleStatement.description)
        assertEquals(TimeNode("5 AM"), scheduleStatement.time)
        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
        assertEquals(LocationNode("UBC"), scheduleStatement.location)
        assertEquals(DateNode("october 14th, october 15th"), scheduleStatement.dates)
    }

    //add more specific format tests
}
