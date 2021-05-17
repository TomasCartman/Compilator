package parser.produtions

import parser.utils.Utils
import parser.utils.Utils.Companion.nextToken
import parser.utils.Utils.Companion.peekNextToken
import utils.Token

class ArithmeticOperator {
    companion object {
        fun plus(tokenBuffer: MutableList<Token>) {
            if (isNextTokenPlusSymbol(tokenBuffer.peekNextToken())) {
                tokenBuffer.nextToken()
            } else {
                Utils.throwParserError(listOf("+"), tokenBuffer)
            }
        }

        fun minus(tokenBuffer: MutableList<Token>) {
            if (isNextTokenMinusSymbol(tokenBuffer.peekNextToken())) {
                tokenBuffer.nextToken()
            } else {
                Utils.throwParserError(listOf("-"), tokenBuffer)
            }
        }

        fun times(tokenBuffer: MutableList<Token>) {
            if (isNextTokenTimesSymbol(tokenBuffer.peekNextToken())) {
                tokenBuffer.nextToken()
            } else {
                Utils.throwParserError(listOf("*"), tokenBuffer)
            }
        }

        fun division(tokenBuffer: MutableList<Token>) {
            if (isNextTokenDivisionSymbol(tokenBuffer.peekNextToken())) {
                tokenBuffer.nextToken()
            } else {
                Utils.throwParserError(listOf("/"), tokenBuffer)
            }
        }

        fun isNextTokenPlusSymbol(token: Token): Boolean = token.value == "+"

        fun isNextTokenMinusSymbol(token: Token): Boolean = token.value == "-"

        fun isNextTokenTimesSymbol(token: Token): Boolean = token.value == "*"

        fun isNextTokenDivisionSymbol(token: Token): Boolean = token.value == "/"
    }
}