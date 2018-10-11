package interpreter

import utils.Constant.STATEMENT_DIVIDER_REGEX
import utils.Grammar.isAtKey
import utils.Grammar.isScheduleKey
import utils.Grammar.isWithKey

class ScheduleStatement(scheduleString: String) : Statement(scheduleString) {

    lateinit var description: String
    lateinit var time: TimeNode
    lateinit var duration: DurationNode
    lateinit var location: LocationNode
    lateinit var dates: Array<DateNode>
    lateinit var guests: Array<GuestNode>

    init {
        val scheduleStatementIterator = scheduleString.split(STATEMENT_DIVIDER_REGEX).iterator()
        val key = scheduleStatementIterator.next()
        while (scheduleStatementIterator.hasNext()) {
            if (isScheduleKey(key)) {
                description = scheduleStatementIterator.next()
            }
            else if (isAtKey(scheduleStatementIterator.next())) {
                location = LocationNode(scheduleStatementIterator.next())
            }
            duration = DurationNode(scheduleStatementIterator.next())
            dates = arrayOf(DateNode(scheduleStatementIterator.next()))
            //this is a unit - parsing by words doesn't really mean anything
            if (isWithKey(scheduleStatementIterator.next())) {
                guests = arrayOf(GuestNode(scheduleStatementIterator.next()))
            }
        }
    }

    override fun interp() {
        //TODO: implement
    }
}
