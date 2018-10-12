package interpreter
import utils.Constant.STATEMENT_DIVIDER_REGEX

import utils.Constant.EMAIL_REGEX

class GuestNode(emailString: String) : Node(emailString) {

    var email: String

    init {
        if (EMAIL_REGEX.matches(emailString)) {
            email = emailString
        } else {
            throw ParseException("Email $emailString is invalid")
        }
    }

    override fun interp() {
        //TODO: implement
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is GuestNode -> this.email.equals(other.email)
            else -> false
        }
    }
}
