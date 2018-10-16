package io

import biweekly.Biweekly
import biweekly.ICalendar
import biweekly.component.VEvent
import biweekly.property.Contact
import interpreter.DurationNode
import interpreter.ScheduleStatement
import java.util.*

fun constructICal(statements: MutableList<ScheduleStatement>) : String {
    val iCal = ICalendar()
    for (scheduleStatement in statements) {
        println(scheduleStatement)

        for (date in scheduleStatement.dates) {
            val preciseDate = date.clone() as Date
            preciseDate.hours = scheduleStatement.time!!.hour
            preciseDate.minutes = scheduleStatement.time!!.minute
            val event = VEvent()

            event.setSummary(scheduleStatement.description!!)
            event.setDateStart(preciseDate)
            if (scheduleStatement.duration == null) {
                event.setDuration(DurationNode("1 hour").toDuration())
            } else {
                event.setDuration(scheduleStatement.duration!!.toDuration())
            }
            if (scheduleStatement.location != null) {
                event.setLocation(scheduleStatement.location!!.location)
            }
            event.contacts.addAll(scheduleStatement.guests.map {
                Contact(it.email)
            })

            iCal.addEvent(event)
        }
    }


    return Biweekly.write(iCal).go()
}