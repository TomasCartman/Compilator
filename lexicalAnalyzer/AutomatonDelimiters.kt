package lexicalAnalyzer

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
                        ';' -> 1
                        ',' -> 2
                        '.' -> 3
                        '(' -> 4
                        ')' -> 5
                        '[' -> 6
                        ']' -> 7
                        '{' -> 8
                        '}' -> 9
                        else -> 10
                    }
                }
                else -> state = 10
            }
        }

        return state == 1 || state == 2 || state == 3 || state == 4 || state == 5
                || state == 6 || state == 7 || state == 8 || state == 9
    }
}