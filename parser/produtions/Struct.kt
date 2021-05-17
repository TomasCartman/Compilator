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
import parser.utils.Utils
import parser.utils.Utils.Companion.hasNextToken
import parser.utils.Utils.Companion.nextToken
import parser.utils.Utils.Companion.peekNextToken
import utils.Token

class Struct {
    companion object {
        fun structUsage(tokenBuffer: MutableList<Token>) {
            if (tokenBuffer.hasNextToken() && isTokenIdentifier(tokenBuffer.peekNextToken())) {
                identifier(tokenBuffer)
                if (tokenBuffer.hasNextToken() && isNextTokenDot(tokenBuffer.peekNextToken())) {
                    dot(tokenBuffer)
                    if (tokenBuffer.hasNextToken() && isTokenIdentifier(tokenBuffer.peekNextToken())) {
                        identifier(tokenBuffer)
                    } else {
                        Utils.throwParserError(listOf("Identifier"), tokenBuffer)
                    }
                } else {
                    Utils.throwParserError(listOf("."), tokenBuffer)
                }
            } else {
                Utils.throwParserError(listOf("Identifier"), tokenBuffer)
            }
        }

        fun structDeclaration(tokenBuffer: MutableList<Token>) {
            if (tokenBuffer.hasNextToken() && tokenBuffer.peekNextToken().value == "typedef") {
                tokenBuffer.nextToken() // Consume typedef
                if (tokenBuffer.hasNextToken() && tokenBuffer.peekNextToken().value == "struct") {
                    tokenBuffer.nextToken() // Consume struct
                    if (tokenBuffer.hasNextToken() && tokenBuffer.peekNextToken().value == "extends") {
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
                    Utils.throwParserError(listOf("struct"), tokenBuffer)
                }
            } else {
                Utils.throwParserError(listOf("typedef"), tokenBuffer)
            }
        }

        private fun structDefinition(tokenBuffer: MutableList<Token>) {
            varDeclaration(tokenBuffer)
            if (tokenBuffer.hasNextToken() && varDeclarationTerminals.contains(tokenBuffer.peekNextToken().value)) {
                varDeclaration(tokenBuffer)
            }
        }

        fun isStructDeclaration(token: Token): Boolean = token.value == "typedef"
    }
}