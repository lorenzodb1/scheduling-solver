package interpreter

import utils.Constant.STATEMENT_DIVIDER_REGEX
import utils.Grammar.isLetKey

class ScheduleStatement(scheduleString: String) : Statement(scheduleString) {

    lateinit var description: String
    lateinit var time: TimeNode
    lateinit var duration: DurationNode
    lateinit var location: LocationNode
    lateinit var dates: Array<DateNode>
    lateinit var guests: Array<GuestNode>

    init {
        // TODO: Properly init
        description = ""
        time = TimeNode("")
        duration = DurationNode("")
        location = LocationNode("")
        dates = arrayOf<DateNode>()
        guests = arrayOf<GuestNode>()
    }

    override fun interp() {
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is ScheduleStatement -> {
                this.description.equals(other.description)
                this.time.equals(other.time)
                this.duration.equals(other.duration)
                this.location.equals(other.location)
                this.dates.equals(other.dates)
                this.guests.equals(other.guests)
            }
            else -> false
        }
    }
}