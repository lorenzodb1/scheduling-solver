package utils

object Constant {

    internal val STATEMENT_DIVIDER_REGEX: Regex = Regex("""/\{([^}]+)\}|([^\s]+)/g""")

    internal val COMMA_REGEX: Regex = Regex("""/([^,]+)/g""")

    internal val EMAIL_REGEX: Regex = Regex("""^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))${'$'}""")
}
