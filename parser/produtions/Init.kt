package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.exceptions.ParserException
import parser.produtions.Delimiters.Companion.closingCurlyBracket
import parser.produtions.Delimiters.Companion.isNextTokenClosingCurlyBracket
import parser.produtions.Delimiters.Companion.openingCurlyBracket
import parser.produtions.Statement.Companion.statement
import parser.utils.Utils.Companion.hasNextToken
import parser.utils.Utils.Companion.nextToken
import parser.utils.Utils.Companion.peekNextToken
import utils.Token

class Init {
    companion object {
        fun init(tokenBuffer: MutableList<Token>) {
            try {
                if(isTokenValueProcedure(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                    start(tokenBuffer)
                } else {
                    throw ParserException(tokenBuffer.peekNextToken().line, tokenBuffer.peekNextToken(), listOf("procedure"))
                }
            } catch (e: NextTokenNullException) {
                throw ParserException(0, null, listOf("procedure"))
            }
        }

        private fun start(tokenBuffer: MutableList<Token>) {
            try {
                if(isTokenValueStart(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                    openingCurlyBracket(tokenBuffer)
                    program(tokenBuffer)
                    closingCurlyBracket(tokenBuffer)
                } else {
                    throw ParserException(tokenBuffer.peekNextToken().line, tokenBuffer.peekNextToken(), listOf("start"))
                }
            } catch (e: NextTokenNullException) {
                throw ParserException(0, null, listOf("start"))
            }
        }

        private fun program(tokenBuffer: MutableList<Token>) {
            statement(tokenBuffer)
            if(tokenBuffer.hasNextToken() && !isNextTokenClosingCurlyBracket(tokenBuffer.peekNextToken())) {
                program(tokenBuffer)
            }
        }

        private fun isTokenValueProcedure(token: Token): Boolean = token.value == "procedure"

        private fun isTokenValueStart(token: Token): Boolean = token.value == "start"

    }
}