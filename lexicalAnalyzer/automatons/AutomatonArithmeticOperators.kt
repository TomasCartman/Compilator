package lexicalAnalyzer.automatons

import utils.ClassType
import utils.Token

class AutomatonArithmeticOperators : Automaton() {

    override fun generateToken(): Token {
        val type = ClassType.createArOpType()
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
                        '+' -> States.ADDITION.state
                        '-' -> States.SUBTRACTION.state
                        '*' -> States.MULTIPLICATION.state
                        '/' -> States.DIVISION.state
                        else -> States.INVALID.state
                    }
                }
                States.ADDITION.state -> {
                    state = if(char == '+') States.AUTOINCREMENT.state
                    else States.INVALID.state
                }
                States.SUBTRACTION.state -> {
                    state = if(char == '-') States.AUTODECREMENT.state
                    else States.INVALID.state
                }
                else -> {
                    state = States.INVALID.state
                    break
                }
            }
        }

        return state in States.ADDITION.state..States.AUTODECREMENT.state
    }

    private enum class States(val state: Int) {
        ADDITION(1),
        SUBTRACTION(2),
        MULTIPLICATION(3),
        DIVISION(4),
        AUTOINCREMENT(5),
        AUTODECREMENT(6),
        INVALID(7)
    }
}