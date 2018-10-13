import org.junit.jupiter.api.TestInstance

import interpreter.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // This annotation is required for @BeforeAll to work
class ScheduleTest {

    //[ ] SCHEDULE := SCHEDULE <NODE> AT <NODE> [FOR <NODE>] [AT LOCATION <NODE>] [ON [EVERY ]<NODE> [AND <NODE>]*  [UNTIL <NODE>]][WITH (<NODE>|<NODESET>)]

    @Test
    fun basic_example_with_all_elements() {
        val scheduleString = "SCHEDULE Fencing AT 5 am FOR 2 hours IN UBC ON EVERY Sunday AND Monday UNTIL October 17th WITH leticia.c.nakajima@gmail.com"

        val ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(("Fencing"), ScheduleStatement.description)
        assertEquals(TimeNode("5 am"), ScheduleStatement.time)
        assertEquals(DurationNode("2 hours"), ScheduleStatement.duration)
        assertEquals(LocationNode("UBC"), ScheduleStatement.location)
        assertEquals(DateNode("october 14th, october 15th"), ScheduleStatement.dates)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)


    }

        @Test
        fun time_written_a_little_differently() {
            val scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"

            val ScheduleStatement = ScheduleStatement(scheduleString);

            assertEquals(("Fencing"), ScheduleStatement.description)
            assertEquals(TimeNode("5 AM"), ScheduleStatement.time)
            assertEquals(DurationNode("2 hours"), ScheduleStatement.duration)
            assertEquals(LocationNode("UBC"), ScheduleStatement.location)
            assertEquals(DateNode("october 14th, october 15th"), ScheduleStatement.dates)
            assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)

        }

    @Test
    fun no_duration_node() {
        val scheduleString= "SCHEDULE Fencing AT 5 AM IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"

        val ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(("Fencing"), ScheduleStatement.description)
        assertEquals(TimeNode("5 AM"), ScheduleStatement.time)
        assertEquals(LocationNode("UBC"), ScheduleStatement.location)
        assertEquals(DateNode("october 14th, october 15th"), ScheduleStatement.dates)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)
    }

    @Test
    fun no_location_node() {
        val scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours ON EVERY Monday AND Sunday UNTIL 2nd of November WITH leticia.c.nakajima@gmail.com"

        val ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(("Fencing"), ScheduleStatement.description)
        assertEquals(TimeNode("5 AM"), ScheduleStatement.time)
        assertEquals(DurationNode("2 hours"), ScheduleStatement.duration)
        assertEquals(DateNode("october 14th, october 15th"), ScheduleStatement.dates)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)
    }

    @Test
    fun no_dates_node() {
        val scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC WITH leticia.c.nakajima@gmail.com"

        val ScheduleStatement = ScheduleStatement(scheduleString);

        assertEquals(("Fencing"), ScheduleStatement.description)
        assertEquals(TimeNode("5 AM"), ScheduleStatement.time)
        assertEquals(DurationNode("2 hours"), ScheduleStatement.duration)
        assertEquals(LocationNode("UBC"), ScheduleStatement.location)
        assertEquals(GuestNode("leticia.c.nakajima@gmail.com"), ScheduleStatement.guests)
    }

    @Test
    fun no_guest_node() {
        val scheduleString = "SCHEDULE Fencing AT 5 AM FOR 2 hours IN UBC ON EVERY Monday AND Sunday UNTIL 2nd of November"

        val scheduleStatement = ScheduleStatement(scheduleString);
        assertEquals(("Fencing"), scheduleStatement.description)
        assertEquals(TimeNode("5 AM"), scheduleStatement.time)
        assertEquals(DurationNode("2 hours"), scheduleStatement.duration)
        assertEquals(LocationNode("UBC"), scheduleStatement.location)
        assertEquals(DateNode("october 14th, october 15th"), scheduleStatement.dates)
    }

    //add more specific format tests


}