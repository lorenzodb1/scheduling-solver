class LetStatement(letString: String) : Statement(letString) {

    lateinit var id: String
    lateinit var value: Node

    init {
        //TODO: parse letString and initialize id and value
    }

    public override fun interp() {
        //TODO: implement
    }
}