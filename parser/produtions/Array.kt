package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.produtions.Delimiters.Companion.closingSquareBracket
import parser.produtions.Delimiters.Companion.isNextTokenOpeningSquareBracket
import parser.produtions.Delimiters.Companion.openingSquareBracket
import parser.produtions.VarDeclaration.Companion.identifier
import parser.produtions.VarDeclaration.Companion.isTokenIdentifier
import parser.produtions.VarDeclaration.Companion.primary
import parser.utils.Utils.Companion.peekNextToken
import utils.Token

class Array {
    companion object {
        fun arrayUsage(tokenBuffer: MutableList<Token>) {
            try {
                if (isTokenIdentifier(tokenBuffer.peekNextToken())) {
                    identifier(tokenBuffer)
                    openingSquareBracket(tokenBuffer)
                    primary(tokenBuffer)
                    closingSquareBracket(tokenBuffer)
                    if (isNextTokenOpeningSquareBracket(tokenBuffer.peekNextToken())) {
                        openingSquareBracket(tokenBuffer)
                        primary(tokenBuffer)
                        closingSquareBracket(tokenBuffer)
                    }
                }
            } catch (e: NextTokenNullException) {

            }
        }
    }
}