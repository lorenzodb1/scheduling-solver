package interpreter

import java.lang.Exception

class TimeNode(timeString: String) : Node(timeString) {

    // assume 24 hour time:
    // hour is in range [0, 24)
    // minute is in range [0, 60)
    var hour: Int = -1
    var minute: Int = -1

    companion object {
        val numberChars = arrayOf('0','1','2','3','4','5','6','7','8','9')
        enum class State {
            ParsingHour,
            ParsingMinute,
            ParsingAmPm
        }
    }

    init {
        // Remove all spaces from the string
        val trimmedTimeString = timeString.split(' ').joinToString("")

        val timeStringIter = trimmedTimeString.iterator().withIndex()

        var hourString = ""
        var minuteString = ""
        var amPmString = ""

        var state = State.ParsingHour
        while (timeStringIter.hasNext()) {
            val (currIndex, currChar) = timeStringIter.next()
            when (currChar) {
                in numberChars -> {
                    when (state) {
                        State.ParsingHour -> hourString += currChar
                        State.ParsingMinute -> minuteString += currChar
                        State.ParsingAmPm -> throw ParseException("Invalid time in string given to TimeNode: \"$timeString\"")
                    }
                }
                else -> {
                    when (state) {
                        State.ParsingHour -> {
                            when (currChar) {
                                ':' -> state = State.ParsingMinute
                                else -> {
                                    state = State.ParsingAmPm
                                    amPmString += currChar
                                }
                            }
                        }
                        State.ParsingMinute, State.ParsingAmPm -> {
                            amPmString += currChar
                        }
                    }
                }
            }
        }

        try {
            hour = hourString.toInt()
        } catch (e: Exception) {
            throw ParseException("Invalid hour in string given to TimeNode: \"$timeString\"")
        }

        if (minuteString != ""){
            try {
                minute = minuteString.toInt()
            } catch (e: Exception) {
                throw ParseException("Invalid minute in string given to TimeNode: \"$timeString\"")
            }
        } else {
            minute = 0
        }

        parseAmPm(amPmString)
        timeInBounds()
    }

    /**
     * Parses the given am/pm string and sets member variables or
     * throws a ParseException as appropriate
     */
    private fun parseAmPm(str: String) {
        when (str.trim()) {
            "PM","pm","Pm" -> {
                if (hour > 12) {
                    throw ParseException("Conflicting meanings, AM with hour > 12 in TimeNode")
                } else {
                    hour += 12
                }
            }
            "AM", "Am", "am" -> {
                if (hour > 12) {
                    throw ParseException("Conflicting meanings, AM with hour > 12 in TimeNode")
                }
            }
            !in arrayOf("AM", "Am", "am", "") -> throw ParseException("Invalid AM/PM in TimeNode \"$str\"")
        }
    }

    private fun timeInBounds(): Boolean {
        return hour <= 24 && hour >= 0 && minute <= 60 && minute >= 0
    }

    override fun interp() {
        //TODO: implement
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is TimeNode -> {
                this.hour.equals(other.hour) &&
                this.minute.equals(other.minute)
            }
            else -> false
        }
    }
}