package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.exceptions.ParserException
import parser.produtions.Delimiters.Companion.closingCurlyBracket
import parser.produtions.Delimiters.Companion.isNextTokenClosingCurlyBracket
import parser.produtions.Delimiters.Companion.openingCurlyBracket
import parser.produtions.Statement.Companion.statement
import parser.utils.Utils
import parser.utils.Utils.Companion.addParserException
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
                    Utils.throwParserError(listOf("procedure"), tokenBuffer)
                }
            } catch (e: NextTokenNullException) {
                Utils.throwParserError(listOf("procedure"), tokenBuffer)
            } catch (e: ParserException) {
                tokenBuffer.addParserException(e)
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
                    Utils.throwParserError(listOf("start"), tokenBuffer)
                }
            } catch (e: NextTokenNullException) {
                Utils.throwParserError(listOf("start"), tokenBuffer)
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