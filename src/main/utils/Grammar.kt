package utils

object Grammar {

    private val letStatementKeys: Array<String> = arrayOf("LET")
    private val forStatementKeys: Array<String> = arrayOf("FOR")
    private val endForStatementKeys: Array<String> = arrayOf("ENDFOR")
    private val scheduleStatementKeys: Array<String> = arrayOf("SCHEDULE")
    private val allStatementKeys: Array<String> = letStatementKeys + forStatementKeys + scheduleStatementKeys
    private val inKeys: Array<String> = arrayOf("IN")
    private val atKeys: Array<String> = arrayOf("AT")
    private val withKeys: Array<String> = arrayOf("WITH")

    //TODO - lorenzodb1: Make sure you require at least the first three char of a month
    private val monthArray: Array<String> = arrayOf("January",
                                                    "February",
                                                    "March",
                                                    "April",
                                                    "May",
                                                    "June",
                                                    "July",
                                                    "August",
                                                    "September",
                                                    "October",
                                                    "November",
                                                    "December")

    private val dayArray: Array<String> = arrayOf("Monday",
                                                  "Tuesday",
                                                  "Wednesday",
                                                  "Thursday",
                                                  "Friday",
                                                  "Saturday",
                                                  "Sunday")

    private val durationArray: Array<String> = arrayOf("hours",
                                                       "minutes",
                                                       "seconds")

    private val timeArray: Array<String> = arrayOf("AM",
                                                   "PM")

    internal fun isStatementKey(key: String): Boolean {
        return allStatementKeys.indexOf(key) > -1
    }

    internal fun isLetKey(key: String): Boolean {
        return letStatementKeys.indexOf(key) > -1
    }

    internal fun isScheduleKey(key: String): Boolean {
        return scheduleStatementKeys.indexOf(key) > -1
    }

    internal fun isForKey(key: String): Boolean {
        return forStatementKeys.indexOf(key) > -1
    }

    internal fun isEndForKey(key: String): Boolean {
        return endForStatementKeys.indexOf(key) > -1
    }

    internal fun isInKey (key: String): Boolean{
        return inKeys.indexOf(key) > -1
    }

    internal fun isValidId(idKey: String): Boolean {
        return idKey.startsWith("$")
    }

    internal fun isAtKey (key: String): Boolean{
        return atKeys.indexOf(key) > -1
    }

    internal fun isWithKey (key: String): Boolean{
        return withKeys.indexOf(key) > -1
    }

    internal fun isMonthArray (key: String): Boolean{
        return monthArray.indexOf(key) > -1
    }

    internal fun isDurationArray (key: String): Boolean{
        return durationArray.indexOf(key) > -1
    }


}

