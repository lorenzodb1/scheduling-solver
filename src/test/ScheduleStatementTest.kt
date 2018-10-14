import org.junit.jupiter.api.TestInstance

import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class ScheduleStatementTest {

    // TODO: delete me
    //[ ] SCHEDULE := SCHEDULE <NODE> AT <NODE> [FOR <NODE>] [AT LOCATION <NODE>] [ON [EVERY ]<NODE> [AND <NODE>]*  [UNTIL <NODE>]][WITH (<NODE>|<NODESET>)]

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
        var symbolTable = SymbolTable()
        val str = "This is some text,    SCHEDULE       Fencing AT 5 am"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals(str, substitutedStr)
    }

    @Test
    fun substituteVariables_one_variable_nothing_else(){
        var symbolTable = SymbolTable()
        symbolTable.set("X", "hello world!")
        val str = "\$X"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals("hello world!", substitutedStr)
    }

    @Test
    fun substituteVariables_two_same_variables_nothing_else(){
        var symbolTable = SymbolTable()
        symbolTable.set("X", "hello world! ")
        val str = "\$X\$X"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals("hello world! hello world! ", substitutedStr)
    }

    @Test
    fun substituteVariables_two_different_variables_nothing_else(){
        var symbolTable = SymbolTable()
        symbolTable.set("X", "hello world! ")
        symbolTable.set("Y", "SCHEDULE")
        val str = "\$X\$Y"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals("hello world! SCHEDULE", substitutedStr)
    }

    @Test
    fun substituteVariables_one_variable_in_text(){
        var symbolTable = SymbolTable()
        symbolTable.set("Y", "Sleeper Service")
        val str = "GSV - \$Y"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals("GSV - Sleeper Service", substitutedStr)
    }

    @Test
    fun substituteVariables_multiple_variables_in_text(){
        var symbolTable = SymbolTable()
        symbolTable.set("X", "5pm")
        symbolTable.set("Y", "dinner")
        val str = "SCHEDULE \$Y AT \$X"
        val substitutedStr = ScheduleStatement.subsituteVariables(str, symbolTable)
        assertEquals("SCHEDULE dinner AT 5pm", substitutedStr)
    }

    @Test
    fun substituteVariables_one_variable_not_in_symbol_table(){
        var symbolTable = SymbolTable()
        symbolTable.set("Y", "dinner")
        val str = "SCHEDULE \$Z AT 5pm"
        val exception = assertThrows(InterpException::class.java) {
            ScheduleStatement.subsituteVariables(str, symbolTable)
        }
    }

    @Test
    fun substituteVariables_multiple_variables_one_not_in_symbol_table(){
        var symbolTable = SymbolTable()
        symbolTable.set("Y", "dinner")
        val str = "SCHEDULE \$Y AT \$X"
        val exception = assertThrows(InterpException::class.java) {
            ScheduleStatement.subsituteVariables(str, symbolTable)
        }
    }

    @Test
    fun substituteVariables_multiple_variables_all_not_in_symbol_table(){
        var symbolTable = SymbolTable()
        symbolTable.set("Z", "dinner")
        val str = "SCHEDULE \$Y AT \$X"
        val exception = assertThrows(InterpException::class.java) {
            ScheduleStatement.subsituteVariables(str, symbolTable)
        }
    }

//    @Test
//    fun basic_example_with_all_elements() {
//        val scheduleString = "SCHEDULE Fencing AT 5 am FOR 2 hours IN UBC ON EVERY Sunday AND Monday UNTIL October 17th WITH leticia.c.nakajima@gmail.com"
//
//        val scheduleStatement = ScheduleStatement(scheduleString);
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
//            val scheduleStatement = ScheduleStatement(scheduleString);
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
//        val scheduleStatement = ScheduleStatement(scheduleString);
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
//        val scheduleStatement = ScheduleStatement(scheduleString);
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
//        val scheduleStatement = ScheduleStatement(scheduleString);
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
//        val scheduleStatement = ScheduleStatement(scheduleString);
//        assertEquals(("Fencing"), scheduleStatement.description)
//        assertEquals(TimeNode("5 AM"), scheduleStatement.time)
//        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
//        assertEquals(LocationNode("UBC"), scheduleStatement.location)
//        assertEquals(DateNode("october 14th, october 15th"), scheduleStatement.dates)
//    }

    //add more specific format tests


}