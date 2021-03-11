package lexicalAnalyzer

import utils.ClassType
import utils.Token

class AutomatonArithmeticOperators : Automaton() {

    override fun generateToken(): Token {
        val type = ClassType.createArOpType()
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
                        '+' -> 1
                        '-' -> 2
                        '*' -> 3
                        '/' -> 4
                        else -> 7
                    }
                }
                1 -> {
                    state = if(char == '+') 5
                    else 7
                }
                2 -> {
                    state = if(char == '-') 6
                    else 7
                }
                else -> {
                    state = 7
                    break
                }
            }
        }

        return state == 1 || state == 2 || state == 3 || state == 4 || state == 5 || state == 6
    }
}