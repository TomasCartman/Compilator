package lexicalAnalyzer.automatons

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
                    state = if(char.isDigit()) States.INT.state
                     else States.INVALID.state
                }
                States.INT.state -> {
                    state = when {
                        char.isDigit() -> States.INT.state
                        char == '.' -> States.INT_WITH_POINT.state
                        else -> States.INVALID.state
                    }
                }
                States.INT_WITH_POINT.state -> {
                    state = if(char.isDigit()) States.FLOAT.state
                    else States.INVALID.state
                }
                States.FLOAT.state -> {
                    state = if(char.isDigit()) States.FLOAT.state
                    else States.INVALID.state
                }
                else -> {
                    state = States.INVALID.state
                    break
                }
            }
        }

        return state == States.INT.state || state == States.FLOAT.state
    }

    private enum class States(val state: Int) {
        INT(1),
        INT_WITH_POINT(2),
        FLOAT(3),
        INVALID(4)
    }
}