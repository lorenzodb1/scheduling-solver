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

    init UnsupportedEncodingException{
        val letScheduleIterator = letString.split(STATEMENT_DIVIDER_REGEX).iterator()
        val key = letScheduleIterator.next()
        while (letStatementIterator.hasNext()) {
            if (isScheduleKey(key)) {
                description = letScheduleIterator.next()
            } return null
            if (isAtKey(key.letScheduleIterator.next())) {
                location = LocationNode(letScheduleIterator.next())
            } return null
            duration = DurationNode(letScheduleIterator.next())
            dates = DateNode(letScheduleIterator.next())
            //this is a unit - parsing by words doesn't really mean anything
            if (isWithKey(letScheduleIterator.next())) {
                guests = GuestNode(letScheduleIterator.next())
            } return null

        }
    }

    override fun interp() {
        //TODO: implement
    }
}