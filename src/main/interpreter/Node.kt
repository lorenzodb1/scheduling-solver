package interpreter

abstract class Node(s: String) {
    /**
     * TODO: better description?
     * Interprete the interpreter.Node
     *
     * Takes a symbol table and returns the symbol table with any updates
     */
    abstract fun interp(symbolTable: SymbolTable)

}