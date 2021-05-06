package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.produtions.Delimiters.Companion.closingCurlyBracket
import parser.produtions.Delimiters.Companion.closingParenthesis
import parser.produtions.Delimiters.Companion.comma
import parser.produtions.Delimiters.Companion.isNextTokenClosingCurlyBracket
import parser.produtions.Delimiters.Companion.isNextTokenClosingParenthesis
import parser.produtions.Delimiters.Companion.isNextTokenComma
import parser.produtions.Delimiters.Companion.isNextTokenOpeningParenthesis
import parser.produtions.Delimiters.Companion.isNextTokenSemicolon
import parser.produtions.Delimiters.Companion.openingCurlyBracket
import parser.produtions.Delimiters.Companion.openingParenthesis
import parser.produtions.Delimiters.Companion.semicolon
import parser.produtions.Expression.Companion.expression
import parser.produtions.Statement.Companion.simpleStatement
import parser.produtions.VarDeclaration.Companion.identifier
import parser.produtions.VarDeclaration.Companion.isTokenIdentifier
import parser.produtions.VarDeclaration.Companion.isTokenPrimary
import parser.produtions.VarDeclaration.Companion.type
import parser.utils.Utils.Companion.hasNextToken
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

        fun functionDeclaration(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenFunctionDeclaration(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken() // Consume function
                    type(tokenBuffer)
                    identifier(tokenBuffer)
                    openingParenthesis(tokenBuffer)
                    params(tokenBuffer)
                    closingParenthesis(tokenBuffer)
                    blockFunction(tokenBuffer)
                }
            } catch (e : NextTokenNullException) {

            }
        }

        private fun blockFunction(tokenBuffer: MutableList<Token>) {
            try {
                openingCurlyBracket(tokenBuffer)
                simpleStatement(tokenBuffer)
                while (tokenBuffer.hasNextToken() && !isNextTokenReturn(tokenBuffer.peekNextToken())) {
                    simpleStatement(tokenBuffer)
                }
                returnMethod(tokenBuffer)
                closingCurlyBracket(tokenBuffer)
            } catch (e: NextTokenNullException) {

            }
        }

        private fun returnMethod(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenReturn(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken() // Consume return
                    if (isTokenIdentifier(tokenBuffer.peekNextToken())) {
                        identifier(tokenBuffer)
                        if (isNextTokenOpeningParenthesis(tokenBuffer.peekNextToken())) {
                            tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                            callFunction(tokenBuffer)
                            semicolon(tokenBuffer)
                        } else {
                            tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                            expression(tokenBuffer)
                            semicolon(tokenBuffer)
                        }
                    } else if(isNextTokenSemicolon(tokenBuffer.peekNextToken())) {
                        semicolon(tokenBuffer)
                    } else {
                        expression(tokenBuffer)
                        semicolon(tokenBuffer)
                    }
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun procedureDeclaration(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenProcedureDeclaration(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken() // Consume procedure
                    identifier(tokenBuffer)
                    openingParenthesis(tokenBuffer)
                    params(tokenBuffer)
                    closingParenthesis(tokenBuffer)
                    blockProcedure(tokenBuffer)
                }
            } catch (e : NextTokenNullException) {

            }
        }

        private fun blockProcedure(tokenBuffer: MutableList<Token>) {
            try {
                openingCurlyBracket(tokenBuffer)
                simpleStatement(tokenBuffer)
                while (tokenBuffer.hasNextToken() && !isNextTokenClosingCurlyBracket(tokenBuffer.peekNextToken())) {
                    simpleStatement(tokenBuffer)
                }
                closingCurlyBracket(tokenBuffer)
            } catch (e: NextTokenNullException) {

            }
        }

        private fun params(tokenBuffer: MutableList<Token>) {
            try {
                if (!isNextTokenClosingParenthesis(tokenBuffer.peekNextToken())) {
                    param(tokenBuffer)
                    if (isNextTokenComma(tokenBuffer.peekNextToken())) {
                        comma(tokenBuffer)
                        params(tokenBuffer)
                    }
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun param(tokenBuffer: MutableList<Token>) {
            try {
                type(tokenBuffer)
                identifier(tokenBuffer)
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

        fun isNextTokenFunctionDeclaration(token: Token): Boolean = token.value == "function"

        fun isNextTokenProcedureDeclaration(token: Token): Boolean = token.value == "procedure"

        fun isNextTokenReturn(token: Token): Boolean = token.value == "return"
    }
}