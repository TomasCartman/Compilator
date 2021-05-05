package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.exceptions.ParserException
import parser.produtions.Delimiters.Companion.closingCurlyBracket
import parser.utils.Utils.Companion.peekNextToken
import parser.produtions.Delimiters.Companion.isNextTokenOpeningCurlyBracket
import parser.produtions.Delimiters.Companion.isNextTokenOpeningParenthesis
import parser.produtions.Delimiters.Companion.openingCurlyBracket
import parser.produtions.VarDeclaration.Companion.identifier
import parser.produtions.VarDeclaration.Companion.isTokenIdentifier
import parser.produtions.VarDeclaration.Companion.isTokenVariableScopeType
import parser.produtions.VarDeclaration.Companion.varDeclaration
import parser.produtions.VarDeclaration.Companion.variableScope
import parser.produtions.VarDeclaration.Companion.variableUsage
import parser.utils.Utils.Companion.addParserException
import parser.utils.Utils.Companion.nextToken
import parser.utils.Utils.Companion.removeLastReadTokenAndPutBackInTokenList
import utils.Token

class Statement {
    companion object {
        private val varDeclarationTerminals = listOf("var", "const")
        private val functionDeclaration = listOf("function", "procedure")

        fun statement(tokenBuffer: MutableList<Token>) {
            try {
                val tokenPeek = tokenBuffer.peekNextToken()
                if (isNextTokenOpeningCurlyBracket(tokenPeek)) {
                    openingCurlyBracket(tokenBuffer)
                    simpleStatement(tokenBuffer)
                    closingCurlyBracket(tokenBuffer)
                } else {
                    simpleStatement(tokenBuffer)
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun simpleStatement(tokenBuffer: MutableList<Token>) {
            try {
                val tokenPeekInsideBrackets = tokenBuffer.peekNextToken()
                if (isVarDeclaration(tokenPeekInsideBrackets)) {
                    varDeclaration(tokenBuffer)
                } else if (isFunctionDeclaration(tokenPeekInsideBrackets)) {

                } else if(isTokenVariableScopeType(tokenPeekInsideBrackets)) {
                  variableScope(tokenBuffer)
                } else if (isTokenIdentifier(tokenPeekInsideBrackets)) {
                    identifier(tokenBuffer)
                    if (isNextTokenOpeningParenthesis(tokenBuffer.peekNextToken())) { // Function call
                        tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                    } else { // Variable usage
                        tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                        variableUsage(tokenBuffer)
                    }
                }
            } catch (e: NextTokenNullException) {

            } catch (e: ParserException) {
                tokenBuffer.addParserException(e)
                statement(tokenBuffer)
            }
        }

        private fun isVarDeclaration(token: Token): Boolean = varDeclarationTerminals.contains(token.value)

        private fun isFunctionDeclaration(token: Token): Boolean = functionDeclaration.contains(token.value)
    }
}