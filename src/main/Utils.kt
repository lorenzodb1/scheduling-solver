object Utils {

    internal val STATEMENT_DIVIDER_REGEX: Regex = Regex("""\{([^}]+)\}|([^\s]+)/g""")
    internal val COMMA_REGEX: Regex = Regex("/([^,]+)/g")
}
