package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.exceptions.ParserException
import parser.produtions.Delimiters.Companion.closingCurlyBracket
import parser.produtions.Delimiters.Companion.dot
import parser.produtions.Delimiters.Companion.isNextTokenDot
import parser.produtions.Delimiters.Companion.openingCurlyBracket
import parser.produtions.Delimiters.Companion.semicolon
import parser.produtions.VarDeclaration.Companion.identifier
import parser.produtions.VarDeclaration.Companion.isTokenIdentifier
import parser.produtions.VarDeclaration.Companion.varDeclaration
import parser.produtions.VarDeclaration.Companion.varDeclarationTerminals
import parser.utils.Utils.Companion.nextToken
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

        fun structDeclaration(tokenBuffer: MutableList<Token>) {
            try {
                if (tokenBuffer.peekNextToken().value == "typedef") {
                    tokenBuffer.nextToken() // Consume typedef
                    if (tokenBuffer.peekNextToken().value == "struct") {
                        tokenBuffer.nextToken() // Consume struct
                        if (tokenBuffer.peekNextToken().value == "extends") {
                            tokenBuffer.nextToken() // Consume extends
                            identifier(tokenBuffer)
                            openingCurlyBracket(tokenBuffer)
                            structDefinition(tokenBuffer)
                            closingCurlyBracket(tokenBuffer)
                            identifier(tokenBuffer)
                            semicolon(tokenBuffer)
                        } else {
                            openingCurlyBracket(tokenBuffer)
                            structDefinition(tokenBuffer)
                            closingCurlyBracket(tokenBuffer)
                            identifier(tokenBuffer)
                            semicolon(tokenBuffer)
                        }
                    } else {
                        throw ParserException(tokenBuffer.peekNextToken().line, tokenBuffer.peekNextToken(), listOf("struct"))
                    }
                } else {
                    throw ParserException(tokenBuffer.peekNextToken().line, tokenBuffer.peekNextToken(), listOf("typedef"))
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun structDefinition(tokenBuffer: MutableList<Token>) {
            try {
                varDeclaration(tokenBuffer)
                if (varDeclarationTerminals.contains(tokenBuffer.peekNextToken().value)) {
                    varDeclaration(tokenBuffer)
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun isStructDeclaration(token: Token): Boolean = token.value == "typedef"
    }
}