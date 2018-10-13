import org.junit.jupiter.api.TestInstance

import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.text.ParseException
import CurrentDate
import CurrentTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class ScheduleTest {

    //[ ] SCHEDULE := SCHEDULE <NODE> AT <NODE> [FOR <NODE>] [AT LOCATION <NODE>] [ON [EVERY ]<NODE> [AND <NODE>]*  [UNTIL <NODE>]][WITH (<NODE>|<NODESET>)]

    @Test
    fun basic_example_with_all_elements() {
        var scheduleString = "SCHEDULE Fencing AT 5 am FOR 2 hours IN UBC ON EVERY Sunday AND Monday UNTIL October 17th WITH leticia.c.nakajima@gmail.com"

        var ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(IdNode("Fencing"), ScheduleStatement.description)
        assertEquals(IdNode("5 am"), ScheduleStatement.time)
        assertEquals(IdNode("2 hours"), ScheduleStatement.duration)
        assertEquals(IdNode("UBC"), ScheduleStatement.location)
        assertEquals(IdNode("october 14th, october 15th"), ScheduleStatement.dates)
        assertEquals(IdNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)


    }

        @Test
        fun time_written_a_little_differently() {
            var scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"

            var ScheduleStatement = ScheduleStatement(scheduleString);

            assertEquals(IdNode("Fencing"), ScheduleStatement.description)
            assertEquals(IdNode("5 AM"), ScheduleStatement.time)
            assertEquals(IdNode("2 hours"), ScheduleStatement.duration)
            assertEquals(IdNode("UBC"), ScheduleStatement.location)
            assertEquals(IdNode("october 14th, october 15th"), ScheduleStatement.dates)
            assertEquals(IdNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)
        }

    @Test
    fun no_duration_node() {
        var scheduleString= "SCHEDULE Fencing AT 5 AM IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"

        var ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(IdNode("Fencing"), ScheduleStatement.description)
        assertEquals(IdNode("5 am"), ScheduleStatement.time)
        assertEquals(IdNode("UBC"), ScheduleStatement.location)
        assertEquals(IdNode("october 14th, october 15th"), ScheduleStatement.dates)
        assertEquals(IdNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)
    }

    @Test
    fun no_location_node() {
        var scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"

        var ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(IdNode("Fencing"), ScheduleStatement.description)
        assertEquals(IdNode("5 am"), ScheduleStatement.time)
        assertEquals(IdNode("2 hours"), ScheduleStatement.duration)
        assertEquals(IdNode("october 14th, october 15th"), ScheduleStatement.dates)
        assertEquals(IdNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)
    }

    @Test
    fun no_dates_node() {
        var scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC WITH leticia.c.nakajima@gmail.com"

        var ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(IdNode("Fencing"), ScheduleStatement.description)
        assertEquals(IdNode("5 am"), ScheduleStatement.time)
        assertEquals(IdNode("2 hours"), ScheduleStatement.duration)
        assertEquals(IdNode("UBC"), ScheduleStatement.location)
        assertEquals(IdNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)
    }

    @Test
    fun no_guest_node() {
        var scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November"

        var ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(IdNode("Fencing"), ScheduleStatement.description)
        assertEquals(IdNode("5 am"), ScheduleStatement.time)
        assertEquals(IdNode("2 hours"), ScheduleStatement.duration)
        assertEquals(IdNode("UBC"), ScheduleStatement.location)
        assertEquals(IdNode("october 14th, october 15th"), ScheduleStatement.dates)
    }






}