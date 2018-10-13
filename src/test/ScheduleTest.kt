import org.junit.jupiter.api.TestInstance

import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.text.ParseException
import CurrentDate
import CurrentTime
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class ScheduleTest {

    //[ ] SCHEDULE := SCHEDULE <NODE> AT <NODE> [FOR <NODE>] [AT LOCATION <NODE>] [ON [EVERY ]<NODE> [AND <NODE>]*  [UNTIL <NODE>]][WITH (<NODE>|<NODESET>)]

    @Test
    fun basic_example_with_all_elements() {
        var scheduleString = "SCHEDULE Fencing AT 5 am FOR 2 hours IN UBC ON EVERY Sunday AND Monday UNTIL October 17th WITH leticia.c.nakajima@gmail.com"

        var ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(("Fencing"), ScheduleStatement.description)
        assertEquals(TimeNode("5 am"), ScheduleStatement.time)
        assertEquals(DurationNode("2 hours"), ScheduleStatement.duration)
        assertEquals(LocationNode("UBC"), ScheduleStatement.location)
        assertEquals(DateNode("october 14th, october 15th"), ScheduleStatement.dates)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)


    }

        @Test
        fun time_written_a_little_differently() {
            var scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"

            var ScheduleStatement = ScheduleStatement(scheduleString);

            assertEquals(("Fencing"), ScheduleStatement.description)
            assertEquals(TimeNode("5 AM"), ScheduleStatement.time)
            assertEquals(DurationNode("2 hours"), ScheduleStatement.duration)
            assertEquals(LocationNode("UBC"), ScheduleStatement.location)
            assertEquals(DateNode("october 14th, october 15th"), ScheduleStatement.dates)
            assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)

        }

    @Test
    fun no_duration_node() {
        var scheduleString= "SCHEDULE Fencing AT 5 AM IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"

        var ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(("Fencing"), ScheduleStatement.description)
        assertEquals(TimeNode("5 AM"), ScheduleStatement.time)
        assertEquals(LocationNode("UBC"), ScheduleStatement.location)
        assertEquals(DateNode("october 14th, october 15th"), ScheduleStatement.dates)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)
    }

    @Test
    fun no_location_node() {
        var scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"

        var ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(("Fencing"), ScheduleStatement.description)
        assertEquals(TimeNode("5 AM"), ScheduleStatement.time)
        assertEquals(DurationNode("2 hours"), ScheduleStatement.duration)
        assertEquals(DateNode("october 14th, october 15th"), ScheduleStatement.dates)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)
    }

    @Test
    fun no_dates_node() {
        var scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC WITH leticia.c.nakajima@gmail.com"

        var ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(("Fencing"), ScheduleStatement.description)
        assertEquals(TimeNode("5 AM"), ScheduleStatement.time)
        assertEquals(DurationNode("2 hours"), ScheduleStatement.duration)
        assertEquals(LocationNode("UBC"), ScheduleStatement.location)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)
    }

    @Test
    fun no_guest_node() {
        var scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November"

        var ScheduleStatement = ScheduleStatement(scheduleString);
        assertEquals(("Fencing"), ScheduleStatement.description)
        assertEquals(TimeNode("5 AM"), ScheduleStatement.time)
        assertEquals(DurationNode("2 hours"), ScheduleStatement.duration)
        assertEquals(LocationNode("UBC"), ScheduleStatement.location)
        assertEquals(DateNode("october 14th, october 15th"), ScheduleStatement.dates)
    }

    //add more specific format tests


}