package io

import biweekly.Biweekly
import biweekly.ICalendar
import biweekly.component.VEvent
import biweekly.property.Contact
import interpreter.ScheduleStatement
import java.util.*

fun constructICal(statements: MutableList<ScheduleStatement>) : String {
    val iCal = ICalendar()
    for (scheduleStatement in statements) {
        for (date in scheduleStatement.dates) {
            val preciseDate = date.clone() as Date
            preciseDate.hours = scheduleStatement.time!!.hour
            preciseDate.minutes = scheduleStatement.time!!.minute
            val event = VEvent()

            event.setSummary(scheduleStatement.description!!)
            event.setDateStart(preciseDate)
            event.setDuration(scheduleStatement.duration!!.toDuration())
            event.setLocation(scheduleStatement.location!!.location)
            event.contacts.addAll(scheduleStatement.guests.map {
                Contact(it.email)
            })

            iCal.addEvent(event)
        }
    }


    return Biweekly.write(iCal).go()
}