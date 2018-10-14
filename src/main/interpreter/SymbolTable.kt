package interpreter

class SymbolTable {
    private val table = HashMap<String, String>()

    fun get(key: String) : String {
        return table[key]!!
    }

    fun set(key: String, value: String) {
        table[key] = value
    }

    fun clear() {
        table.clear()
    }

    fun copy(): SymbolTable {
        val symbolTableCopy = SymbolTable()
        for (entry in table) {
            symbolTableCopy.set(entry.key, entry.value)
        }
        return symbolTableCopy
    }

    operator fun iterator(): MutableIterator<MutableMap.MutableEntry<String, String>> {
        return table.iterator()
    }
}