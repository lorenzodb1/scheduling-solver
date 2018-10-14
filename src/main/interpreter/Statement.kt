package interpreter


abstract class Statement(s: String) {

    abstract fun interp(symbolTable: SymbolTable)
}