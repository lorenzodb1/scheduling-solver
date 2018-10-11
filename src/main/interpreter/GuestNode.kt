package interpreter
import utils.Constant.STATEMENT_DIVIDER_REGEX

class GuestNode(emailString: String) : Node(emailString) {

    lateinit var email: String

    init {
        val emailIterator = letString.split(STATEMENT_DIVIDER_REGEX).iterator()
        val key = emailIterator.next()
        while (email.hasNext()) {

        }
        //TODO: validate emailString and initialize email
    }

    public override fun interp() {
        //TODO: implement
    }
}