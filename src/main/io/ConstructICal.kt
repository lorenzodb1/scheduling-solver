package io

import interpreter.StatementList
import interpreter.SymbolTable

fun constructICal(statements: StatementList) : String {

    // TODO: Delete me maybe?
    // Interpret the Statements, starting with an empty symbol table
    statements.interp(SymbolTable())

    return "" // TODO: implement
}