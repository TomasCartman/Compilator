package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.utils.Utils.Companion.nextToken
import parser.utils.Utils.Companion.peekNextToken
import utils.Token

class ArithmeticOperator {
    companion object {
        fun plus(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenPlusSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun minus(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenMinusSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun times(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenTimesSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun division(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenDivisionSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun isNextTokenPlusSymbol(token: Token): Boolean = token.value == "+"

        fun isNextTokenMinusSymbol(token: Token): Boolean = token.value == "-"

        fun isNextTokenTimesSymbol(token: Token): Boolean = token.value == "*"

        fun isNextTokenDivisionSymbol(token: Token): Boolean = token.value == "/"
    }
}