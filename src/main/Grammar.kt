internal object Grammar {

    private val statementArray: Array<String> = arrayOf("SCHEDULE",
                                                        "LET",
                                                        "FOR")

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


}

