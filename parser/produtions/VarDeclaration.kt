package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.exceptions.ParserException
import parser.produtions.Delimeters.Companion.closingCurlyBracket
import parser.produtions.Delimeters.Companion.comma
import parser.produtions.Delimeters.Companion.dot
import parser.produtions.Delimeters.Companion.isNextTokenComma
import parser.produtions.Delimeters.Companion.isNextTokenDot
import parser.produtions.Delimeters.Companion.isNextTokenOpeningParenthesis
import parser.produtions.Delimeters.Companion.isNextTokenOpeningSquareBracket
import parser.produtions.Delimeters.Companion.isNextTokenSemicolon
import parser.utils.Utils.Companion.nextToken
import parser.produtions.Delimeters.Companion.openingCurlyBracket
import parser.produtions.Delimeters.Companion.semicolon
import parser.produtions.Expression.Companion.expression
import parser.produtions.RelationalOperators.Companion.assignment
import parser.produtions.RelationalOperators.Companion.isNextTokenAssignmentSymbol
import parser.utils.Utils.Companion.peekNextToken
import parser.utils.Utils.Companion.putTokenBack
import parser.utils.Utils.Companion.removeLastReadTokenAndPutBackInTokenList
import utils.ClassType
import utils.Token

class VarDeclaration {
    companion object {
        private val types = listOf("boolean", "string", "int", "real")
        private val booleanList = listOf("true", "false")
        val variableScopeType = listOf("local", "global")
        val primaryStringListName = listOf("Identifier", "true", "false", "Real", "Decimal", "String")

        fun varDeclaration(tokenBuffer: MutableList<Token>) {
            try {
                val token = tokenBuffer.nextToken()
                if(isVarDeclaration(token)) { // Var
                    openingCurlyBracket(tokenBuffer)
                    typedVariable(tokenBuffer)
                    closingCurlyBracket(tokenBuffer)
                } else { // Const
                    openingCurlyBracket(tokenBuffer)
                    typedConst(tokenBuffer)
                    closingCurlyBracket(tokenBuffer)
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun typedVariable(tokenBuffer: MutableList<Token>) {
            try {
                type(tokenBuffer)
                variables(tokenBuffer)
                semicolon(tokenBuffer)
                if(types.contains(tokenBuffer.peekNextToken().value)) {
                    typedVariable(tokenBuffer)
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun typedConst(tokenBuffer: MutableList<Token>) {
            try {
                type(tokenBuffer)
                constants(tokenBuffer)
                semicolon(tokenBuffer)
                if(types.contains(tokenBuffer.peekNextToken().value)) {
                    typedConst(tokenBuffer)
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun constants(tokenBuffer: MutableList<Token>) {
            try {
                constDeclarator(tokenBuffer)
                if(isNextTokenComma(tokenBuffer.peekNextToken())) {
                    constants(tokenBuffer)
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun constDeclarator(tokenBuffer: MutableList<Token>) {
            try {
                if(isTokenIdentifier(tokenBuffer.peekNextToken())) {
                    identifier(tokenBuffer)
                    assignment(tokenBuffer)
                    if(isTokenLiteral(tokenBuffer.peekNextToken())) {
                        tokenBuffer.nextToken() // Consume Literal
                    }
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun type(tokenBuffer: MutableList<Token>) {
            try {
                val tokenPeek = tokenBuffer.peekNextToken()
                if(types.contains(tokenPeek.value)) {
                    tokenBuffer.nextToken()
                } else {
                    throw ParserException(tokenPeek.line, tokenPeek, types)
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun variables(tokenBuffer: MutableList<Token>) {
            try {
                variableDeclarator(tokenBuffer)
                val tokenPeek = tokenBuffer.peekNextToken()
                if(isNextTokenComma(tokenPeek)) {
                    comma(tokenBuffer)
                    variables(tokenBuffer)
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun variableDeclarator(tokenBuffer: MutableList<Token>) {
            try {
                val token = tokenBuffer.nextToken()
                if(isTokenIdentifier(token)) {
                    val tokenPeek = tokenBuffer.peekNextToken()
                    if (isNextTokenAssignmentSymbol(tokenPeek)) {
                        assignment(tokenBuffer)
                        val tokenPeekNext = tokenBuffer.peekNextToken()
                        if(variableScopeType.contains(tokenPeekNext.value)) { // VariableScopeType
                            tokenBuffer.nextToken() // Consume variable scope
                            dot(tokenBuffer)
                            val tokenNext = tokenBuffer.nextToken()
                            if (isTokenIdentifier(tokenNext)) {
                                identifier(tokenBuffer)
                            } else {
                                throw ParserException(tokenNext.line, tokenNext, listOf("Identifier"))
                            }
                        } else if(isTokenIdentifier(tokenPeekNext)) {
                            identifier(tokenBuffer)
                            val tokenPeekThird = tokenBuffer.peekNextToken()
                            if(isNextTokenDot(tokenPeekThird)) { // Struct Usage
                                tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                // StructUsage
                            } else if(isNextTokenOpeningParenthesis(tokenPeekThird)) {
                                tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                // FunctionCall
                            }
                        } else if(isTokenPrimary(tokenPeekNext)) {
                            expression(tokenBuffer)
                        }
                    } else if(isNextTokenOpeningSquareBracket(tokenPeek)) {
                        tokenBuffer.putTokenBack(token)
                        // ArrayUsage
                        val tokenPeekSecond = tokenBuffer.peekNextToken()
                        if(isNextTokenAssignmentSymbol(tokenPeekSecond)) {
                            assignment(tokenBuffer)
                            openingCurlyBracket(tokenBuffer)
                            // VarArgs
                            closingCurlyBracket(tokenBuffer)
                        }
                    }
                } else {
                    throw ParserException(token.line, token, listOf("Identifier"))
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun identifier(tokenBuffer: MutableList<Token>) {
            try {
                if(isTokenIdentifier(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun isVarDeclaration(token: Token): Boolean = token.value == "var"

        fun isTokenIdentifier(token: Token): Boolean = token.type.type == ClassType.ID

        fun isTokenNumber(token: Token): Boolean = token.type.type == ClassType.NUMBER

        fun isTokenString(token: Token): Boolean = token.type.type == ClassType.STRING

        fun isTokenPrimary(token: Token): Boolean {
            return isTokenIdentifier(token) || booleanList.contains(token.value) || isTokenNumber(token) ||
                    isTokenString(token)

        }

        fun isTokenLiteral(token: Token): Boolean {
            return booleanList.contains(token.value) || isTokenNumber(token) || isTokenString(token)
        }
    }
}