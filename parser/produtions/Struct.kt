package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.produtions.Delimeters.Companion.dot
import parser.produtions.Delimeters.Companion.isNextTokenDot
import parser.produtions.VarDeclaration.Companion.identifier
import parser.produtions.VarDeclaration.Companion.isTokenIdentifier
import parser.utils.Utils.Companion.peekNextToken
import utils.Token

class Struct {
    companion object {
        fun structUsage(tokenBuffer: MutableList<Token>) {
            try {
                if (isTokenIdentifier(tokenBuffer.peekNextToken())) {
                    identifier(tokenBuffer)
                    if (isNextTokenDot(tokenBuffer.peekNextToken())) {
                        dot(tokenBuffer)
                        if (isTokenIdentifier(tokenBuffer.peekNextToken())) {
                            identifier(tokenBuffer)
                        }
                    }
                }
            } catch (e: NextTokenNullException) {

            }
        }
    }
}