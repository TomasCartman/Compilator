package lexicalAnalyzer

import utils.ClassType
import utils.Token

class AutomatonLogicalOperators : Automaton() {

    override fun generateToken(): Token {
        val type = ClassType.createLogOpType()
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
                        '&' -> 1
                        '|' -> 2
                        '!' -> 3
                        else -> 6
                    }
                }
                1 -> {
                    state = if(char == '&') 4
                    else 6
                }
                2 -> {
                    state = if(char == '|') 5
                    else 6
                }
                else -> {
                    state = 6
                    break
                }
            }
        }

        return state == 3 || state == 4 || state == 5
    }
}