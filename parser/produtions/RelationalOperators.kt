package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.exceptions.ParserException
import parser.utils.Utils.Companion.nextToken
import parser.utils.Utils.Companion.peekNextToken
import utils.Token


class RelationalOperators {
    companion object {
        fun assignment(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenAssignmentSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                } else {
                    throw ParserException(tokenBuffer.peekNextToken().line, tokenBuffer.peekNextToken(), listOf("="))
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun equal(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenEqualSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun notEqual(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenNotEqualSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun greaterThan(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenGreaterThanSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun lessThan(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenLessThanSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun greaterThanOrEqual(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenGreaterThanOrEqualSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun lessThanOrEqual(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenLessThanOrEqualSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun isNextTokenAssignmentSymbol(token: Token): Boolean = token.value == "="

        fun isNextTokenEqualSymbol(token: Token): Boolean = token.value == "=="

        fun isNextTokenNotEqualSymbol(token: Token): Boolean = token.value == "!="

        fun isNextTokenGreaterThanSymbol(token: Token): Boolean = token.value == ">"

        fun isNextTokenLessThanSymbol(token: Token): Boolean = token.value == "<"

        fun isNextTokenGreaterThanOrEqualSymbol(token: Token): Boolean = token.value == ">="

        fun isNextTokenLessThanOrEqualSymbol(token: Token): Boolean = token.value == "<="
    }
}