class SymbolTable {
    companion object {
        private val table = HashMap<String, Node>()

        public fun get(key: String) : Node {
            return table[key]!!
        }

        public fun set(key: String, value: Node) {
            table[key] = value
        }
    }
}