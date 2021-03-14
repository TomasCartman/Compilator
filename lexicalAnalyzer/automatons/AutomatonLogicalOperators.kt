package lexicalAnalyzer.automatons

import utils.ClassType
import utils.Token

class AutomatonLogicalOperators : Automaton() {

    override fun generateToken(): Token {
        val type = ClassType.createLogOpType()
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
                        '&' -> States.BITWISE_AND.state
                        '|' -> States.BITWISE_OR.state
                        '!' -> States.LOGICAL_NOT.state
                        else -> States.INVALID.state
                    }
                }
                States.BITWISE_AND.state -> {
                    state = if(char == '&') States.LOGICAL_AND.state
                    else States.INVALID.state
                }
                States.BITWISE_OR.state -> {
                    state = if(char == '|') States.LOGICAL_OR.state
                    else States.INVALID.state
                }
                else -> {
                    state = States.INVALID.state
                    break
                }
            }
        }

        return state in States.LOGICAL_NOT.state..States.LOGICAL_OR.state
    }

    private enum class States(val state: Int) {
        BITWISE_AND(1),
        BITWISE_OR(2),
        LOGICAL_NOT(3),
        LOGICAL_AND(4),
        LOGICAL_OR(5),
        INVALID(6)
    }
}