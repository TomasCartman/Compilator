package lexicalAnalyzer

import utils.ClassType
import utils.Token

class AutomatonNumbers {
    private var lexeme: String = ""
    private var state: Int = 0

    fun putNewString(s: String): Boolean {
        resetAutomaton()
        lexeme = s

        return testStringLexeme()
    }

    fun generateToken(): Token {
        val type = ClassType.createNumberType()
        val token = Token(type)
        token.value = lexeme
        return  token
    }

    private fun resetAutomaton() {
        state = 0
    }

    private fun testStringLexeme(): Boolean {

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
                else -> state = 4
            }
        }

        return state == 1 || state == 3
    }

}