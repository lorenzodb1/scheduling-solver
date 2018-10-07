package interpreter

import interpreter.Statement

class StatementList(s: String) {

    lateinit var statements: Array<Statement>


    init {
        //TODO: parse s into a list of statements and initialize properties
    }

    fun interp() {

    }
}