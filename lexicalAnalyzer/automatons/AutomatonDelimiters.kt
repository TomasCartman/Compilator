package lexicalAnalyzer.automatons

import utils.ClassType
import utils.Token

class AutomatonDelimiters : Automaton() {

    override fun generateToken(): Token {
        val type = ClassType.createDelimiterType()
        val token = Token(type)
        token.value = lexeme
        return token
    }

    override fun testStringLexeme(): Boolean {
        if(lexeme.length >= 2) return false

        for(char in lexeme) {
            when(state) {
                0 -> {
                    state = when(char) {
                        ';' -> States.SEMICOLON.state
                        ',' -> States.COMMA.state
                        '.' -> States.DOT.state
                        '(' -> States.LEFT_PARENTHESIS.state
                        ')' -> States.RIGHT_PARENTHESIS.state
                        '[' -> States.LEFT_BRACKET.state
                        ']' -> States.RIGHT_BRACKET.state
                        '{' -> States.LEFT_CURLY_BRACKET.state
                        '}' -> States.RIGHT_CURLY_BRACKET.state
                        else -> States.INVALID.state
                    }
                }
                else -> state = States.INVALID.state
            }
        }

        return state in States.SEMICOLON.state..States.RIGHT_CURLY_BRACKET.state
    }

    private enum class States(val state: Int) {
        SEMICOLON(1),
        COMMA(2),
        DOT(3),
        LEFT_PARENTHESIS(4),
        RIGHT_PARENTHESIS(5),
        LEFT_BRACKET(6),
        RIGHT_BRACKET(7),
        LEFT_CURLY_BRACKET(8),
        RIGHT_CURLY_BRACKET(9),
        INVALID(10)
    }
}