import interpreter.Node

class SymbolTable {
    companion object {
        private val table = HashMap<String, Node>()

        fun get(key: String) : Node {
            return table[key]!!
        }

        fun set(key: String, value: Node) {
            table[key] = value
        }

        fun clear() {
            table.clear()
        }
    }
}