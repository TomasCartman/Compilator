package lexicalAnalyzer.automatons

import utils.ClassType
import utils.Token

class AutomatonComments : Automaton() {

    override fun generateToken(): Token {
        val type = ClassType.createCommentType()
        val token = Token(type)
        token.value = lexeme
        return  token
    }

    override fun testStringLexeme(): Boolean {
        if(lexeme.length > 2) return false

        for(char in lexeme) {
            when(state) {
                0 -> {
                    state = when(char) {
                        '/' -> States.SLASH.state
                        else -> States.INVALID.state
                    }
                }
                States.SLASH.state -> {
                    state = when(char) {
                        '/' -> States.SINGLE_LINE_COMMENT.state
                        '*' -> States.MULTI_LINE_COMMENT.state
                        else -> States.INVALID.state
                    }
                }
                else -> {
                    state = States.INVALID.state
                    break
                }
            }
        }

        return state in States.SINGLE_LINE_COMMENT.state..States.MULTI_LINE_COMMENT.state
    }

    private enum class States(val state: Int) {
        SLASH(1),
        SINGLE_LINE_COMMENT(2),
        MULTI_LINE_COMMENT(3),
        INVALID(4)
    }
}