package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.produtions.Delimeters.Companion.closingCurlyBracket
import parser.utils.Utils.Companion.peekNextToken
import parser.produtions.Delimeters.Companion.isNextTokenOpeningCurlyBracket
import parser.produtions.Delimeters.Companion.isNextTokenOpeningParenthesis
import parser.produtions.Delimeters.Companion.openingCurlyBracket
import parser.produtions.VarDeclaration.Companion.identifier
import parser.produtions.VarDeclaration.Companion.isTokenIdentifier
import parser.produtions.VarDeclaration.Companion.varDeclaration
import parser.produtions.VarDeclaration.Companion.variableUsage
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

            }
        }

        private fun isVarDeclaration(token: Token): Boolean = varDeclarationTerminals.contains(token.value)

        private fun isFunctionDeclaration(token: Token): Boolean = functionDeclaration.contains(token.value)
    }
}