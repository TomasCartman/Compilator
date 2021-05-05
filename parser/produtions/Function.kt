package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.produtions.Delimiters.Companion.closingParenthesis
import parser.produtions.Delimiters.Companion.comma
import parser.produtions.Delimiters.Companion.isNextTokenClosingParenthesis
import parser.produtions.Delimiters.Companion.isNextTokenComma
import parser.produtions.Delimiters.Companion.isNextTokenOpeningParenthesis
import parser.produtions.Delimiters.Companion.openingParenthesis
import parser.produtions.VarDeclaration.Companion.identifier
import parser.produtions.VarDeclaration.Companion.isTokenIdentifier
import parser.produtions.VarDeclaration.Companion.isTokenPrimary
import parser.utils.Utils.Companion.nextToken
import parser.utils.Utils.Companion.peekNextToken
import parser.utils.Utils.Companion.removeLastReadTokenAndPutBackInTokenList
import utils.Token

class Function {
    companion object {
        fun callFunction(tokenBuffer: MutableList<Token>) {
            try {
                if (isTokenIdentifier(tokenBuffer.peekNextToken())) {
                    identifier(tokenBuffer)
                    if (isNextTokenOpeningParenthesis(tokenBuffer.peekNextToken())) {
                        openingParenthesis(tokenBuffer)
                        args(tokenBuffer)
                        if (isNextTokenClosingParenthesis(tokenBuffer.peekNextToken())) {
                            closingParenthesis(tokenBuffer)
                        }
                    }
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun args(tokenBuffer: MutableList<Token>) {
            try {
                arg(tokenBuffer)
                if (isNextTokenComma(tokenBuffer.peekNextToken())) {
                    comma(tokenBuffer)
                    args(tokenBuffer)
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun arg(tokenBuffer: MutableList<Token>) {
            try {
                val tokenPeek = tokenBuffer.peekNextToken()
                if (!isTokenIdentifier(tokenPeek) && isTokenPrimary(tokenPeek)) { // Primary but not identifier
                    tokenBuffer.nextToken() // Consume Primary
                } else if(isTokenIdentifier(tokenPeek)) {
                    identifier(tokenBuffer)
                    if(isNextTokenOpeningParenthesis(tokenBuffer.peekNextToken())) { // Call function
                        tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                        callFunction(tokenBuffer)
                    } else {
                        // Already consumed the identifier
                    }
                }
            } catch (e: NextTokenNullException) {

            }
        }
    }
}