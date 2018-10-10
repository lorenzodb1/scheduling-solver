package interpreter

class GuestNode(emailString: String) : Node(emailString) {

    lateinit var email: String

    init {
        //TODO: validate emailString and initialize email
    }

    public override fun interp() {
        //TODO: implement
    }
}