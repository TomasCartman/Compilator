package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.utils.Utils.Companion.nextToken
import parser.utils.Utils.Companion.peekNextToken
import utils.Token

class LogicalOperators {
    companion object {
        fun logicalOr(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenLogicalOrSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun logicalAnd(tokenBuffer: MutableList<Token>) {
            try {
                if (isNextTokenLogicalAndSymbol(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun isNextTokenLogicalOrSymbol(token: Token): Boolean = token.value == "||"

        fun isNextTokenLogicalAndSymbol(token: Token): Boolean = token.value == "&&"

        fun isNextTokenLogicalNotSymbol(token: Token): Boolean = token.value == "!"
    }
}