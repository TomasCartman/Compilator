package parser.produtions

import parser.produtions.Delimiters.Companion.closingParenthesis
import parser.produtions.Delimiters.Companion.openingParenthesis
import parser.produtions.Expression.Companion.expression
import parser.produtions.Statement.Companion.statement
import parser.utils.Utils.Companion.nextToken
import parser.utils.Utils.Companion.peekNextToken
import utils.Token

class If {
    companion object {
        fun ifStatement(tokenBuffer: MutableList<Token>) {
            tokenBuffer.nextToken() // Consume if
            openingParenthesis(tokenBuffer)
            expression(tokenBuffer)
            closingParenthesis(tokenBuffer)
            statement(tokenBuffer)
            if (isNextTokenElse(tokenBuffer.peekNextToken())) {
                tokenBuffer.nextToken() // Consume else
                statement(tokenBuffer)
            }
        }

        fun isNextTokenIf(token: Token): Boolean = token.value == "if"

        private fun isNextTokenElse(token: Token): Boolean = token.value == "else"
    }
}