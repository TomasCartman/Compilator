package lexicalAnalyzer

import utils.ClassType
import utils.Token

class AutomatonIdentifiers {
    private var lexeme: String = ""
    private var state: Int = 0

    fun putNewString(s: String): Boolean {
        resetAutomaton()
        lexeme = s

        return testStringLexeme()
    }

    fun generateToken(): Token {
        val type = ClassType.createIdType()
        val token = Token(type)
        token.value = lexeme
        return  token
    }

    private fun resetAutomaton() {
        state = 0
    }

    private fun testStringLexeme(): Boolean {

        for(char in lexeme) {
            state = when(state) {
                0 -> {
                    if (char.isLetter()) 1
                    else 2
                }
                1 -> {
                    if(char.isLetterOrDigit() || char == '_') 1
                    else 2
                }
                else -> 2
            }
        }

        return state == 1
    }
}