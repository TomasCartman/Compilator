package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.exceptions.ParserException
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
                }
            } catch (e: NextTokenNullException) {
             throw ParserException(0, null, listOf("procedure"))
            }
        }

        private fun start(tokenBuffer: MutableList<Token>) {
            try {
                if(isTokenValueStart(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                    program(tokenBuffer)
                }
            } catch (e: NextTokenNullException) {
                throw ParserException(0, null, listOf("start"))
            }
        }

        private fun program(tokenBuffer: MutableList<Token>) {
            statement(tokenBuffer)
            if(tokenBuffer.hasNextToken()) {
                program(tokenBuffer)
            }
        }

        private fun isTokenValueProcedure(token: Token): Boolean = token.value == "procedure"

        private fun isTokenValueStart(token: Token): Boolean = token.value == "start"

    }
}