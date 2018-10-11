package interpreter

import utils.Constant.STATEMENT_DIVIDER_REGEX
import utils.Grammar.isTimeKey
import utils.Grammar.isLocationKey
import utils.Grammar.isForKey
import utils.Grammar.isOnKey
import utils.Grammar.isScheduleKey
import utils.Grammar.isMapKey

class ScheduleStatement(scheduleString: String) : Statement(scheduleString) {

    //SCHEDULE := SCHEDULE <NODE> AT <NODE> [FOR <NODE>] [AT LOCATION <NODE>] [ON [EVERY ]<NODE> [AND <NODE>]*  [UNTIL <NODE>]][WITH (<NODE>|<NODESET>)]
    //DURATION = "for _"
    //DATE = ON
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
            } else if (isTimeKey(scheduleStatementIterator.next())) {
                time = TimeNode(scheduleStatementIterator.next())
            } else if(isForKey(scheduleStatementIterator.next())){ //"for"
                duration = DurationNode(scheduleStatementIterator.next())
            } else if(isLocationKey(scheduleStatementIterator.next())){ //"in"
                location = LocationNode(scheduleStatementIterator.next())
            } else if(isOnKey(scheduleStatementIterator.next())){
                dates = arrayOf(DateNode(scheduleStatementIterator.next()))
            } else if (isMapKey(scheduleStatementIterator.next())) { //this is a unit - parsing by words doesn't really mean anything
                guests = arrayOf(GuestNode(scheduleStatementIterator.next()))
            }
        }
    }

    override fun interp() {
        //TODO: implement
    }
}
