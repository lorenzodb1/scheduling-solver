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
    }

    override fun interp() {
        //TODO: implement
    }
}