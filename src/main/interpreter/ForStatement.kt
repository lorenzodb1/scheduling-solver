package interpreter

class ForStatement(forString: String) : Statement(forString) {

    var id: IdNode
    var nodeSet: NodeSet
    var statements: StatementList

    init {
        val tokens = forString.split(" ")
        val tokensIter = tokens.iterator().withIndex()

        // Check for starting FOR
        if (!iterateOverWhitespace(tokensIter, "FOR")) {
            throw ParseException("Missing \"FOR\" at start of string given to ForStatement: \"$forString\"")
        }

        // Get the Id
        var nextNonWhitespaceToken = iterateOverWhitespace(tokensIter)
        when (nextNonWhitespaceToken) {
            null -> throw ParseException("No id in string given to ForStatement: \"$forString\"")
            else -> {
                id = IdNode(nextNonWhitespaceToken.value)
            }
        }

        // Check that next word is "IN"
        if (!iterateOverWhitespace(tokensIter, "IN")) {
            throw ParseException("Missing \"IN\" in string given to ForStatement: \"$forString\"")
        }

        // Start of NodeSet
        nextNonWhitespaceToken = iterateOverWhitespace(tokensIter);
        if (nextNonWhitespaceToken == null) {
            throw ParseException("Missing Nodeset in string given to ForStatement: \"$forString\"")
        }
        val nodesetStartIndex = nextNonWhitespaceToken.index
        val nodesetStartToken = nextNonWhitespaceToken.value
        if (nodesetStartToken.length < 1 || nodesetStartToken.trim().first() != '{') {
            throw ParseException("Invalid NodeSet in string given to ForStatement: \"$forString\"")
        }

        // In case there are nested Nodesets, track our depth
        var nodesetDepth = 0
        var currIndex = nodesetStartIndex
        var currToken = tokens[nodesetStartIndex]
        while (tokensIter.hasNext()) {
            val trimmedToken = currToken.trim()
            if (trimmedToken.length > 0 && trimmedToken.first() == '{') {
                nodesetDepth++
            }
            if (trimmedToken.length > 0 && trimmedToken.last() == '}') {
                nodesetDepth--
            }
            if (nodesetDepth == 0) {
                currIndex++
                break
            }
            val nextIndexAndToken = tokensIter.next()
            currIndex = nextIndexAndToken.index
            currToken = nextIndexAndToken.value
        }
        val nodeSetEndIndex = currIndex

        // Check if we went all the way to the end (nodeset was opened but not closed)
        if (!tokensIter.hasNext()) {
            throw ParseException("NodeSet not closed in string given to ForStatement: \"$forString\"")
        }

        // Construct our Nodeset
        nodeSet = NodeSet(tokens.subList(nodesetStartIndex, nodeSetEndIndex).joinToString(" "))

        // Start of statements
        var numTokensInStatements = 0
        var forStatementDepth = 1
        currIndex = -1
        var currentToken = ""
        loop@ while (tokensIter.hasNext()) {
            val currIndexAndToken = tokensIter.next()
            currIndex = currIndexAndToken.index
            currToken = currIndexAndToken.value
            numTokensInStatements++
            when (currToken.trim()) {
                "FOR" -> forStatementDepth++
                "ENDFOR" -> {
                    forStatementDepth--
                    if (forStatementDepth == 0) {
                        break@loop;
                    }
                }
            }
        }
        val statementsEndIndex = currIndex

        // Check for unmatched FOR, ENDFOR statements
        if (forStatementDepth > 0 || tokens[currIndex].trim() != "ENDFOR") {
            throw ParseException("Found FOR with no matching ENDFOR in: \"$forString\"")
        } else if (forStatementDepth < 0) {
            throw ParseException("Found ENDFOR with no matching FOR in: \"$forString\"")
        }

        statements = StatementList(tokens.subList(
                statementsEndIndex - numTokensInStatements+1,
                statementsEndIndex).joinToString(" "))
    }

    /**
     * Keeps iterating until it hits a non-empty string or the end
     *
     * If the non-empty string is the given keyword (ignoring leading and trailing spaces),
     * then returns true, else returns false
     */
    private fun iterateOverWhitespace(iter: Iterator<IndexedValue<String>>, keyword: String): Boolean {
        while (iter.hasNext()) {
            val currentToken = iter.next().value
            if (currentToken.trim() != "") {
                return currentToken.trim() == keyword
            }
        }
        return false
    }

    /**
     * Keeps iterating until it hits a non-empty string or the end
     *
     * Returns the first non-empty string with the index (index, token) or null if at the end
     */
    private fun iterateOverWhitespace(iter: Iterator<IndexedValue<String>>): IndexedValue<String>? {
        while (iter.hasNext()) {
            val (currentIndex, currentToken) = iter.next()
            if (currentToken.trim() != "") {
                return IndexedValue(currentIndex, currentToken)
            }
        }
        return null
    }

    override fun interp(symbolTable: SymbolTable) : MutableList<ScheduleStatement> {
        val out: MutableList<ScheduleStatement> = mutableListOf()
        for (node_value in nodeSet.nodes) {
            // Make a copy of the symbol table so that anything happening in this FOR loop
            // can't effect scope outside the FOR loop
            val symbolTableCopy = symbolTable.copy()
            symbolTableCopy.set(id.id, node_value)

            // Make a copy of the Statments so that things happening in this iteration of the FOR loop can't effect
            // things happening in the next
            val statementsCopy = statements.copy()
            val nextStatements = statementsCopy.interp(symbolTableCopy)
            out.addAll(nextStatements)
        }

        return out
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is ForStatement -> {
                this.id.equals(other.id) &&
                        this.nodeSet.equals(other.nodeSet) &&
                        this.statements.equals(other.statements)
            }
            else -> false
        }
    }
}