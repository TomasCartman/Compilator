package lexicalAnalyzer.automatons

import utils.ClassType
import utils.Token

class AutomatonRelationalOperators : Automaton() {

    override fun generateToken(): Token {
        val type = ClassType.createRelOpType()
        val token = Token(type)
        token.value = lexeme
        return token
    }

    override fun testStringLexeme(): Boolean {
        if(lexeme.length > 2) return false

        for(char in lexeme) {
            when(state) {
                0 -> {
                    state = when(char) {
                        '=' -> States.ASSIGNMENT.state
                        '!' -> States.EXCLAMATION.state
                        '>' -> States.GREATER_THAN.state
                        '<' -> States.LESS_THAN.state
                        else -> States.INVALID.state
                    }
                }
                States.ASSIGNMENT.state -> {
                    state = if(char == '=') States.EQUALS.state
                    else States.INVALID.state
                }
                States.EXCLAMATION.state -> {
                    state = if(char == '=') States.NOT_EQUAL.state
                    else States.INVALID.state
                }
                States.GREATER_THAN.state -> {
                    state = if(char == '=') States.GREATER_THAN_OR_EQUAL.state
                    else States.INVALID.state
                }
                States.LESS_THAN.state -> {
                    state = if(char == '=') States.LESS_THAN_OR_EQUAL.state
                    else States.INVALID.state
                }
                else -> {
                    state = States.INVALID.state
                    break
                }
            }
        }

        return state in States.ASSIGNMENT.state..States.EQUALS.state ||
                state in States.NOT_EQUAL.state..States.LESS_THAN_OR_EQUAL.state
    }

    private enum class States(val state: Int) {
        ASSIGNMENT(1),
        EQUALS(2),
        EXCLAMATION(3),
        NOT_EQUAL(4),
        GREATER_THAN(5),
        GREATER_THAN_OR_EQUAL(6),
        LESS_THAN(7),
        LESS_THAN_OR_EQUAL(8),
        INVALID(9)
    }
}