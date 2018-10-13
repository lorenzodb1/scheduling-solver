package interpreter

class DurationNode(durationString: String) : Node(durationString) {
    var minutes = 0

    init {
        val durationStringTrimmedAndLowerCase = durationString.trim().toLowerCase()
        val matches = Regex("^(\\d+)\\s+([A-z]+)$")
                .matchEntire(durationStringTrimmedAndLowerCase)

        if (matches == null){
            throw ParseException("Invalid string given to DurationNode: $durationString")
        }

        var numericValue = 0
        try {
            numericValue = matches.groupValues[1].toInt()
        } catch (e: Exception) {
            throw ParseException("Invalid numeric value in string given to DurationNode: $durationString")
        }

        val unitString = matches.groupValues[2]
        when (unitString) {
            "hours" -> minutes = numericValue * 60
            "minutes" -> minutes = numericValue * 1
            else -> throw ParseException("Invalid units in string given to DurationNode: $durationString")
        }
    }

    override fun interp(symbolTable: SymbolTable): SymbolTable {
        return symbolTable
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is DurationNode -> this.minutes.equals(other.minutes)
            else -> false
        }
    }
}
