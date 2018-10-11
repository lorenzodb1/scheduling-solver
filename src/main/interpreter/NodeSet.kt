package interpreter

/**
 * Note that initString should include the curly braces
 * ex: initString = {a, b, 10 hours, 5pm}
 */
class NodeSet(initString: String) {

    // We actually just store each "node" as a string, and rely on parsing after we
    // substitute it in to catch errors
    lateinit var nodes: Array<String>

    init {
        if (initString.length < 2 || initString.first() != '{' || initString.last() != '}') {
            throw ParseException("Invalid string given to NodeSet: \"" + initString + "\"")
        }

        // Split the string into elements
        nodes = initString.subSequence(1, initString.length-1).split(",").toTypedArray();

        // Strip spaces from the end of each element
        for (i in nodes.indices) {
            nodes[i] = nodes[i].trim()
        }

        // Check for empty list case
        if (nodes[0].trim() == ""){
            nodes = arrayOf()
        }
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is NodeSet -> this.nodes.contentDeepEquals(other.nodes)
            else -> false
        }
    }
}