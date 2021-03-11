package lexicalAnalyzer

import utils.ClassType
import utils.Token

class AutomatonNumbers : Automaton() {

    override fun generateToken(): Token {
        val type = ClassType.createNumberType()
        val token = Token(type)
        token.value = lexeme
        return  token
    }

    override fun testStringLexeme(): Boolean {

        for(char in lexeme) {
            when(state) {
                0 -> {
                    state = if(char.isDigit()) {
                        1
                    } else 4
                }
                1 -> {
                    state = when {
                        char.isDigit() -> {
                            1
                        }
                        char == '.' -> {
                            2
                        }
                        else -> 4
                    }
                }
                2 -> {
                    state = if(char.isDigit()) {
                        3
                    } else 4
                }
                3 -> {
                    state = if(char.isDigit()) {
                        3
                    } else 4
                }
                else -> {
                    state = 4
                    break
                }
            }
        }

        return state == 1 || state == 3
    }

}