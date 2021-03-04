package lexicalAnalyzer

import utils.ClassType
import utils.Token

class AutomatonRelationalOperators {
    private var lexeme: String = ""
    private var state: Int = 0

    fun putNewString(s: String): Boolean {
        resetAutomaton()
        lexeme = s

        return testStringLexeme()
    }

    fun generateToken(): Token {
        val type = ClassType.createRelOpType()
        val token = Token(type)
        when(state) {
            1 -> token.value = "="
            2 -> token.value = "=="
            4 -> token.value = "!="
            5 -> token.value = ">"
            6 -> token.value = ">="
            7 -> token.value ="<"
            8 -> token.value = "<="
        }
        return token
    }

    private fun resetAutomaton() {
        state = 0
    }

    private fun testStringLexeme(): Boolean {
        if(lexeme.length > 2) return false

        for(char in lexeme) {
            when(state) {
                0 -> {
                    state = when(char) {
                        '=' -> 1
                        '!' -> 3
                        '>' -> 5
                        '<' -> 7
                        else -> 9
                    }
                }
                1 -> {
                    state = if(char == '=') 2
                    else 9
                }
                3 -> {
                    state = if(char == '=') 4
                    else 9
                }
                5 -> {
                    state = if(char == '=') 6
                    else 9
                }
                7 -> {
                    state = if(char == '=') 8
                    else 9
                }
                else -> {
                    state = 9
                    break
                }
            }
        }

        return state == 1 || state == 2 || state == 4 || state == 5 || state == 6 || state == 7 || state == 8
    }
}