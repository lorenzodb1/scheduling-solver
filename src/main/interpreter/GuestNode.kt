package interpreter
import utils.Constant.STATEMENT_DIVIDER_REGEX

class GuestNode(emailString: String) : Node(emailString) {

    lateinit var email: String

    init {
        val emailIterator = emailString.split(STATEMENT_DIVIDER_REGEX).iterator()
        val key = emailIterator.next()
        while (emailIterator.hasNext()) {

        }
        //TODO: validate emailString and initialize email
    }

    override fun interp() {
        //TODO: implement
    }
}
