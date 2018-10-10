package interpreter

class DurationNode(s: String) : Node(s) {
    var seconds: Int = 0

    //DURATION := <Num> (hours|minutes|seconds)

    init {
        val durationIterator = letString.split(STATEMENT_DIVIDER_REGEX).iterator()
        val key = durationIterator.next()
        while (durationIterator.hasNext()) {
            if (isDigit(key)) {
                val digit = key
            }
            val magnitude = durationIterator.next();
            if(isDurationArray(magnitude) {
                        if (magnitude == "seconds") {
                            seconds = digit
                        } else if (magnitude == "minutes") {
                            seconds = (digit * 60)
                        } else if (magnitude == "hours") {
                            seconds = (digit * 3600)
                        }
                    }

        }
    }

    public override fun interp() {
        //TODO: implement
    }
}