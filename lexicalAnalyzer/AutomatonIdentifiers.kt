package lexicalAnalyzer

import utils.ClassType
import utils.Token

class AutomatonIdentifiers : Automaton() {

    override fun generateToken(): Token {
        val type = ClassType.createIdType()
        val token = Token(type)
        token.value = lexeme
        return  token
    }

    override fun testStringLexeme(): Boolean {

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