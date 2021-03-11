package lexicalAnalyzer

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
                        '/' -> 1
                        else -> 6
                    }
                }
                1 -> {
                    state = when(char) {
                        '/' -> 3
                        '*' -> 4
                        else -> 6
                    }
                }
                else -> {
                    state = 6
                    break
                }
            }
        }

        return state == 3 || state == 4
    }
}