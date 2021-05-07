package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.exceptions.ParserException
import parser.utils.Utils.Companion.lastValidToken
import parser.utils.Utils.Companion.nextToken
import parser.utils.Utils.Companion.peekNextToken
import parser.utils.Utils.Companion.peekNextTokenOrNull
import utils.Token

class Delimiters {
    companion object {
        fun openingCurlyBracket(tokenBuffer: MutableList<Token>) {
            try {
                if(isNextTokenOpeningCurlyBracket(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                } else {
                    throwDelimitersError("{", tokenBuffer)
                }
            } catch (e: NextTokenNullException) {
                throwDelimitersError("{", tokenBuffer)
            }
        }

        fun closingCurlyBracket(tokenBuffer: MutableList<Token>) {
            try {
                if(isNextTokenClosingCurlyBracket(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                } else {
                    throwDelimitersError("}", tokenBuffer)
                }
            } catch (e: NextTokenNullException) {
                throwDelimitersError("}", tokenBuffer)
            }
        }

        fun semicolon(tokenBuffer: MutableList<Token>) {
            try {
                if(isNextTokenSemicolon(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                } else {
                    throwDelimitersError(";", tokenBuffer)
                }
            } catch (e: NextTokenNullException) {
                throwDelimitersError(";", tokenBuffer)
            }
        }

        fun comma(tokenBuffer: MutableList<Token>) {
            try {
                if(isNextTokenComma(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                } else {
                    throwDelimitersError(",", tokenBuffer)
                }
            } catch (e: NextTokenNullException) {
                throwDelimitersError(",", tokenBuffer)
            }
        }

        fun dot(tokenBuffer: MutableList<Token>) {
            try {
                if(isNextTokenDot(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                } else {
                    throwDelimitersError(".", tokenBuffer)
                }
            } catch (e: NextTokenNullException) {
                throwDelimitersError(".", tokenBuffer)
            }
        }

        fun openingParenthesis(tokenBuffer: MutableList<Token>) {
            try {
                if(isNextTokenOpeningParenthesis(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                } else {
                    throwDelimitersError("(", tokenBuffer)
                }
            } catch (e: NextTokenNullException) {
                throwDelimitersError("(", tokenBuffer)
            }
        }

        fun closingParenthesis(tokenBuffer: MutableList<Token>) {
            try {
                if(isNextTokenClosingParenthesis(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                } else {
                    throwDelimitersError(")", tokenBuffer)
                }
            } catch (e: NextTokenNullException) {
                throwDelimitersError(")", tokenBuffer)
            }
        }

        fun openingSquareBracket(tokenBuffer: MutableList<Token>) {
            try {
                if(isNextTokenOpeningSquareBracket(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                } else {
                    throwDelimitersError("[", tokenBuffer)
                }
            } catch (e: NextTokenNullException) {
                throwDelimitersError("[", tokenBuffer)
            }
        }

        fun closingSquareBracket(tokenBuffer: MutableList<Token>) {
            try {
                if(isNextTokenClosingSquareBracket(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                } else {
                    throwDelimitersError("]", tokenBuffer)
                }
            } catch (e: NextTokenNullException) {
                throwDelimitersError("]", tokenBuffer)
            }
        }

        fun isNextTokenOpeningCurlyBracket(token: Token): Boolean = token.value == "{"

        fun isNextTokenClosingCurlyBracket(token: Token): Boolean = token.value == "}"

        fun isNextTokenSemicolon(token: Token): Boolean = token.value == ";"

        fun isNextTokenComma(token: Token): Boolean = token.value == ","

        fun isNextTokenOpeningSquareBracket(token: Token): Boolean = token.value == "["

        fun isNextTokenClosingSquareBracket(token: Token): Boolean = token.value == "]"

        fun isNextTokenDot(token: Token): Boolean = token.value == "."

        fun isNextTokenOpeningParenthesis(token: Token): Boolean = token.value == "("

        fun isNextTokenClosingParenthesis(token: Token): Boolean = token.value == ")"

        private fun throwDelimitersError(expectedSymbol: String, tokenBuffer: MutableList<Token>) {
            val lastValidToken = tokenBuffer.lastValidToken()
            val actualToken = tokenBuffer.peekNextTokenOrNull()

            if (actualToken != null && lastValidToken != null) {
                val actualTokenCopy = actualToken.copy(line = lastValidToken.line)
                throw ParserException(actualTokenCopy.line, actualTokenCopy, listOf(expectedSymbol))
            } else if(lastValidToken != null) {
                throw ParserException(lastValidToken.line, lastValidToken, listOf(expectedSymbol))
            } else {
                throw ParserException(0, null, listOf(expectedSymbol))
            }
        }
    }
}