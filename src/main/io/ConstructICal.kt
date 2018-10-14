package io

import biweekly.ICalendar
import interpreter.ScheduleStatement

fun constructICal(statements: MutableList<ScheduleStatement>) : String {
    val iCal = ICalendar()
    for (scheduleStatement in statements) {
        //TODO: add events to iCal
    }
    return ""
}