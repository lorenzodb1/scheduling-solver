package interpreter

/**
 * Note that initString should include the curly braces
 * ex: initString = {a, b, 10 hours, 5pm}
 */
class NodeSet(initString: String) {

    lateinit var nodes: Array<Node>

    init {
        //NOTE: validate the existence of curly braces in initString
        //TODO: parse initString and populate nodes
    }
}