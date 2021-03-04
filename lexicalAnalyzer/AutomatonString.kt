package lexicalAnalyzer

import utils.ClassType
import utils.Token

class AutomatonString {
    private var lexeme: String = ""
    private var state: Int = 0

    fun putNewString(s: String): Boolean {
        resetAutomaton()
        lexeme = s

        return testStringLexeme()
    }

    fun generateToken(): Token {
        val type = ClassType.createStringType()
        val token = Token(type)
        token.value = lexeme
        return token
    }

    private fun resetAutomaton() {
        state = 0
    }

    private fun testStringLexeme(): Boolean {
        for(char in lexeme) {
            when(state) {
                0 -> {
                    state = if(char == '"') 1
                    else 4
                }
                1 -> {
                    state = if(char.isLetterOrDigit() || isValidSymbol(char.toInt()) || char == '\"') 2
                    else 4
                }
                2 -> {
                    state = if(char.isLetterOrDigit() || isValidSymbol(char.toInt())) 2
                    else if(char == '"') 3
                    else 4
                }
                else -> {
                    state = 4
                    break
                }
            }

        }

        return state == 1 || state == 2 || state == 3
    }

    private fun isValidSymbol(charCode: Int): Boolean {
        return (charCode in 32..33) || (charCode in 35..126)
    }
}