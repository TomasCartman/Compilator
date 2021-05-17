package parser.produtions

import parser.produtions.Delimiters.Companion.closingParenthesis
import parser.produtions.Delimiters.Companion.openingParenthesis
import parser.produtions.Expression.Companion.expression
import parser.produtions.Statement.Companion.statement
import parser.utils.Utils.Companion.nextToken
import utils.Token

class While {
    companion object {
        fun whileStatement(tokenBuffer: MutableList<Token>) {
            tokenBuffer.nextToken() // Consume while
            openingParenthesis(tokenBuffer)
            expression(tokenBuffer)
            closingParenthesis(tokenBuffer)
            statement(tokenBuffer)
        }

        fun isNextTokenWhile(token: Token): Boolean = token.value == "while"
    }
}