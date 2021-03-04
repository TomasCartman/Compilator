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
                    else 6
                }
                1 -> {
                    state = if(char == '\\') 3
                    else if(char.isLetterOrDigit() || isValidSymbol(char.toInt())) 2
                    else 6
                }
                2 -> {
                    state = if(char == '\\') 3
                    else if(char.isLetterOrDigit() || isValidSymbol(char.toInt())) 2
                    else if(char == '"') 5
                    else 6
                }
                3 -> {
                    state = if(char == '"') 4
                    else 6
                }
                4 -> {
                    state = if(char == '\\') 3
                    else if(char.isLetterOrDigit() || isValidSymbol(char.toInt())) 2
                    else 6
                }
                else -> {
                    state = 6
                    break
                }
            }

        }

        return state == 1 || state == 2 || state == 3 || state == 4 || state == 5
    }

    private fun isValidSymbol(charCode: Int): Boolean {
        return (charCode in 32..33) || (charCode in 35..126)
    }
}