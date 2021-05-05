package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.exceptions.ParserException
import parser.produtions.Array.Companion.arrayUsage
import parser.produtions.Delimiters.Companion.closingCurlyBracket
import parser.produtions.Delimiters.Companion.comma
import parser.produtions.Delimiters.Companion.dot
import parser.produtions.Delimiters.Companion.isNextTokenClosingCurlyBracket
import parser.produtions.Delimiters.Companion.isNextTokenComma
import parser.produtions.Delimiters.Companion.isNextTokenDot
import parser.produtions.Delimiters.Companion.isNextTokenOpeningParenthesis
import parser.produtions.Delimiters.Companion.isNextTokenOpeningSquareBracket
import parser.produtions.Delimiters.Companion.isNextTokenSemicolon
import parser.utils.Utils.Companion.nextToken
import parser.produtions.Delimiters.Companion.openingCurlyBracket
import parser.produtions.Delimiters.Companion.semicolon
import parser.produtions.Expression.Companion.expression
import parser.produtions.Function.Companion.callFunction
import parser.produtions.RelationalOperators.Companion.assignment
import parser.produtions.RelationalOperators.Companion.isNextTokenAssignmentSymbol
import parser.produtions.Struct.Companion.structUsage
import parser.utils.Utils.Companion.peekNextToken
import parser.utils.Utils.Companion.removeLastReadTokenAndPutBackInTokenList
import utils.ClassType
import utils.Token

class VarDeclaration {
    companion object {
        private val types = listOf("boolean", "string", "int", "real")
        private val booleanList = listOf("true", "false")
        private val variableScopeType = listOf("local", "global")
        val primaryStringListName = listOf("Identifier", "true", "false", "Real", "Decimal", "String")

        fun varDeclaration(tokenBuffer: MutableList<Token>) {
            try {
                val token = tokenBuffer.nextToken()
                when {
                    isVarDeclaration(token) -> { // Var
                        openingCurlyBracket(tokenBuffer)
                        typedVariable(tokenBuffer)
                        closingCurlyBracket(tokenBuffer)
                    }
                    isConstDeclaration(token) -> { // Const
                        openingCurlyBracket(tokenBuffer)
                        typedConst(tokenBuffer)
                        closingCurlyBracket(tokenBuffer)
                    }
                    else -> {
                        throw ParserException(token.line, token, listOf("var", "const"))
                    }
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
                        literal(tokenBuffer)
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
                            variableScopeType(tokenBuffer)
                            dot(tokenBuffer)
                            val tokenPeekNext = tokenBuffer.peekNextToken()
                            if (isTokenIdentifier(tokenPeekNext)) {
                                identifier(tokenBuffer)
                            } else {
                                throw ParserException(tokenPeekNext.line, tokenPeekNext, listOf("Identifier"))
                            }
                        } else if(isTokenIdentifier(tokenPeekNext)) {
                            identifier(tokenBuffer)
                            val tokenPeekThird = tokenBuffer.peekNextToken()
                            if(isNextTokenDot(tokenPeekThird)) { // Struct Usage
                                tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                structUsage(tokenBuffer)
                            } else if(isNextTokenOpeningParenthesis(tokenPeekThird)) { // Call function
                                tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                callFunction(tokenBuffer)
                            }
                        } else if(isTokenPrimary(tokenPeekNext)) {
                            expression(tokenBuffer)
                        } else {
                            throw ParserException(tokenPeekNext.line, tokenPeekNext, listOf("local", "global",
                                "Identifier", "true", "false", "real", "int", "string"))
                        }
                    } else if(isNextTokenOpeningSquareBracket(tokenPeek)) { // Array Usage
                        tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                        arrayUsage(tokenBuffer)
                        val tokenPeekSecond = tokenBuffer.peekNextToken()
                        if(isNextTokenAssignmentSymbol(tokenPeekSecond)) {
                            assignment(tokenBuffer)
                            openingCurlyBracket(tokenBuffer)
                            varArgs(tokenBuffer)
                            closingCurlyBracket(tokenBuffer)
                        }
                    }
                } else {
                    throw ParserException(token.line, token, listOf("Identifier"))
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun varArgs(tokenBuffer: MutableList<Token>) {
            try {
                if (!isNextTokenClosingCurlyBracket(tokenBuffer.peekNextToken())) {
                    varArg(tokenBuffer)
                    if (isNextTokenComma(tokenBuffer.peekNextToken())) {
                        comma(tokenBuffer)
                        varArgs(tokenBuffer)
                    }
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun varArg(tokenBuffer: MutableList<Token>) {
            try {
                primary(tokenBuffer)
            } catch (e: NextTokenNullException) {

            }
        }

        fun variableUsage(tokenBuffer: MutableList<Token>) {
            if (isTokenIdentifier(tokenBuffer.peekNextToken())) {
                identifier(tokenBuffer)
                if (isNextTokenOpeningSquareBracket(tokenBuffer.peekNextToken())) { // Array usage
                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                    arrayUsage(tokenBuffer)
                    val tokenPeek = tokenBuffer.peekNextToken()
                    if (isNextTokenAssignmentSymbol(tokenPeek)) {
                        assignment(tokenBuffer)
                        val tokenPeekNext = tokenBuffer.peekNextToken()
                        if (isTokenLiteral(tokenPeekNext)) {
                            expression(tokenBuffer)
                            semicolon(tokenBuffer)
                        } else if(isTokenIdentifier(tokenPeekNext)) {
                            identifier(tokenBuffer)
                            val tokenPeekThird = tokenBuffer.peekNextToken()
                            when {
                                isNextTokenDot(tokenPeekThird) -> { // struct
                                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                    structUsage(tokenBuffer)
                                    semicolon(tokenBuffer)
                                }
                                isNextTokenOpeningSquareBracket(tokenPeekThird) -> { // array
                                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                    arrayUsage(tokenBuffer)
                                    semicolon(tokenBuffer)
                                }
                                isNextTokenOpeningParenthesis(tokenPeekThird) -> { // function
                                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                    callFunction(tokenBuffer)
                                    semicolon(tokenBuffer)
                                }
                                else -> { // variable init
                                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                    expression(tokenBuffer)
                                    semicolon(tokenBuffer)
                                }
                            }
                        }
                    } else if (isNextTokenSemicolon(tokenPeek)) {
                        semicolon(tokenBuffer)
                    }
                } else if (isNextTokenDot(tokenBuffer.peekNextToken())) { // Struct usage
                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                    structUsage(tokenBuffer)
                    val tokenPeek = tokenBuffer.peekNextToken()
                    if (isNextTokenAssignmentSymbol(tokenPeek)) {
                        assignment(tokenBuffer)
                        val tokenPeekNext = tokenBuffer.peekNextToken()
                        if (isTokenLiteral(tokenPeekNext)) {
                            expression(tokenBuffer)
                            semicolon(tokenBuffer)
                        } else if(isTokenIdentifier(tokenPeekNext)) {
                            identifier(tokenBuffer)
                            val tokenPeekThird = tokenBuffer.peekNextToken()
                            when {
                                isNextTokenDot(tokenPeekThird) -> { // struct
                                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                    structUsage(tokenBuffer)
                                    semicolon(tokenBuffer)
                                }
                                isNextTokenOpeningSquareBracket(tokenPeekThird) -> { // array
                                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                    arrayUsage(tokenBuffer)
                                    semicolon(tokenBuffer)
                                }
                                isNextTokenOpeningParenthesis(tokenPeekThird) -> { // function
                                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                    callFunction(tokenBuffer)
                                    semicolon(tokenBuffer)
                                }
                                else -> { // variable init
                                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                    expression(tokenBuffer)
                                    semicolon(tokenBuffer)
                                }
                            }
                        }
                    } else if(isNextTokenSemicolon(tokenPeek)) {
                        semicolon(tokenBuffer)
                    }
                } else { // Variable Declarator
                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                    variableDeclarator(tokenBuffer)
                    semicolon(tokenBuffer)
                }
            }
        }

        fun identifier(tokenBuffer: MutableList<Token>) {
            try {
                if(isTokenIdentifier(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                } else {
                    throw ParserException(tokenBuffer.peekNextToken().line, tokenBuffer.peekNextToken(), listOf("Identifier"))
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun primary(tokenBuffer: MutableList<Token>) {
            try {
                val tokenPeek = tokenBuffer.peekNextToken()
                if (isTokenIdentifier(tokenPeek)) {
                    identifier(tokenBuffer)
                } else if(isTokenLiteral(tokenPeek)) {
                    literal(tokenBuffer)
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun literal(tokenBuffer: MutableList<Token>) {
            try {
                if (isTokenLiteral(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                } else {
                    throw ParserException(tokenBuffer.peekNextToken().line, tokenBuffer.peekNextToken(),
                        listOf("true", "false", "real", "int", "string"))
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun variableScopeType(tokenBuffer: MutableList<Token>) {
            try {
                if(isTokenVariableScopeType(tokenBuffer.peekNextToken())) {
                    tokenBuffer.nextToken()
                }
            } catch (e: NextTokenNullException) {

            }
        }

        fun variableScope(tokenBuffer: MutableList<Token>) {
            try {
                val tokenPeek = tokenBuffer.peekNextToken()
                if (isTokenVariableScopeType(tokenPeek)) {
                    variableScopeType(tokenBuffer)
                    dot(tokenBuffer)
                    identifier(tokenBuffer)
                    val tokenPeekNext = tokenBuffer.peekNextToken()
                    if (isNextTokenAssignmentSymbol(tokenPeekNext)) {
                        assignment(tokenBuffer)
                        if (isTokenVariableScopeType(tokenBuffer.peekNextToken())) {
                            variableScopeType(tokenBuffer)
                            dot(tokenBuffer)
                            identifier(tokenBuffer)
                            if (isNextTokenOpeningSquareBracket(tokenBuffer.peekNextToken())) {
                                tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                arrayUsage(tokenBuffer)
                                semicolon(tokenBuffer)
                            } else {
                                semicolon(tokenBuffer)
                            }
                        } else if (isTokenIdentifier(tokenBuffer.peekNextToken())) {
                            identifier(tokenBuffer)
                            when {
                                isNextTokenOpeningSquareBracket(tokenBuffer.peekNextToken()) -> {
                                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                    arrayUsage(tokenBuffer)
                                    semicolon(tokenBuffer)
                                }
                                isNextTokenDot(tokenBuffer.peekNextToken()) -> {
                                    tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                                    structUsage(tokenBuffer)
                                    semicolon(tokenBuffer)
                                }
                                else -> semicolon(tokenBuffer)
                            }
                        } else if (isTokenLiteral(tokenBuffer.peekNextToken())) {
                            literal(tokenBuffer)
                            semicolon(tokenBuffer)
                        }
                    } else if (isNextTokenOpeningSquareBracket(tokenPeekNext)) {
                        tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                        arrayUsage(tokenBuffer)
                        assignment(tokenBuffer)
                        variableScopeType(tokenBuffer)
                        dot(tokenBuffer)
                        identifier(tokenBuffer)
                        semicolon(tokenBuffer)
                    } else {
                        tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                        variableUsage(tokenBuffer)
                    }
                } else if (isTokenIdentifier(tokenPeek)) {
                    identifier(tokenBuffer)
                    val tokenPeekNext = tokenBuffer.peekNextToken()
                    if (isNextTokenOpeningSquareBracket(tokenPeekNext)) {
                        tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                        arrayUsage(tokenBuffer)
                        assignment(tokenBuffer)
                        variableScopeType(tokenBuffer)
                        dot(tokenBuffer)
                        identifier(tokenBuffer)
                        semicolon(tokenBuffer)
                    } else if (isNextTokenDot(tokenPeekNext)) {
                        tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                        structUsage(tokenBuffer)
                        assignment(tokenBuffer)
                        variableScopeType(tokenBuffer)
                        dot(tokenBuffer)
                        identifier(tokenBuffer)
                        semicolon(tokenBuffer)
                    }
                }
            } catch (e: NextTokenNullException) {

            }
        }

        private fun isVarDeclaration(token: Token): Boolean = token.value == "var"

        private fun isConstDeclaration(token: Token): Boolean = token.value == "const"

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

        fun isTokenVariableScopeType(token: Token): Boolean = variableScopeType.contains(token.value)
    }
}