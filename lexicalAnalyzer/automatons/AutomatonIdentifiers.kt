package lexicalAnalyzer.automatons

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
                    if (char.isLetter()) States.VALID_IDENTIFIER.state
                    else States.INVALID.state
                }
                States.VALID_IDENTIFIER.state -> {
                    if(char.isLetterOrDigit() || char == '_') States.VALID_IDENTIFIER.state
                    else States.INVALID.state
                }
                else -> States.INVALID.state
            }
        }

        return state == States.VALID_IDENTIFIER.state
    }

    private enum class States(val state: Int) {
        VALID_IDENTIFIER(1),
        INVALID(2)
    }
}