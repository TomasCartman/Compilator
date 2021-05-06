package parser.produtions

import parser.exceptions.NextTokenNullException
import parser.exceptions.ParserException
import parser.produtions.Delimiters.Companion.closingCurlyBracket
import parser.utils.Utils.Companion.peekNextToken
import parser.produtions.Delimiters.Companion.isNextTokenOpeningCurlyBracket
import parser.produtions.Delimiters.Companion.isNextTokenOpeningParenthesis
import parser.produtions.Delimiters.Companion.openingCurlyBracket
import parser.produtions.Function.Companion.callFunction
import parser.produtions.Function.Companion.functionDeclaration
import parser.produtions.Function.Companion.isNextTokenFunctionDeclaration
import parser.produtions.Function.Companion.isNextTokenProcedureDeclaration
import parser.produtions.Function.Companion.procedureDeclaration
import parser.produtions.IO.Companion.isNextTokenPrintStat
import parser.produtions.IO.Companion.isNextTokenReadStat
import parser.produtions.IO.Companion.printStat
import parser.produtions.IO.Companion.readStat
import parser.produtions.Struct.Companion.isStructDeclaration
import parser.produtions.Struct.Companion.structDeclaration
import parser.produtions.VarDeclaration.Companion.identifier
import parser.produtions.VarDeclaration.Companion.isTokenIdentifier
import parser.produtions.VarDeclaration.Companion.isTokenVariableScopeType
import parser.produtions.VarDeclaration.Companion.varDeclaration
import parser.produtions.VarDeclaration.Companion.varDeclarationTerminals
import parser.produtions.VarDeclaration.Companion.variableScope
import parser.produtions.VarDeclaration.Companion.variableUsage
import parser.utils.Utils.Companion.addParserException
import parser.utils.Utils.Companion.nextToken
import parser.utils.Utils.Companion.removeLastReadTokenAndPutBackInTokenList
import utils.Token

class Statement {
    companion object {
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

        fun simpleStatement(tokenBuffer: MutableList<Token>) {
            try {
                val tokenPeekInsideBrackets = tokenBuffer.peekNextToken()
                if (isVarDeclaration(tokenPeekInsideBrackets)) {
                    varDeclaration(tokenBuffer)
                } else if(isTokenVariableScopeType(tokenPeekInsideBrackets)) {
                  variableScope(tokenBuffer)
                } else if(isStructDeclaration(tokenPeekInsideBrackets)) {
                    structDeclaration(tokenBuffer)
                } else if(isNextTokenReadStat(tokenPeekInsideBrackets)) {
                    readStat(tokenBuffer)
                } else if(isNextTokenPrintStat(tokenPeekInsideBrackets)) {
                    printStat(tokenBuffer)
                } else if(isNextTokenFunctionDeclaration(tokenPeekInsideBrackets)) {
                    functionDeclaration(tokenBuffer)
                } else if(isNextTokenProcedureDeclaration(tokenPeekInsideBrackets)) {
                    procedureDeclaration(tokenBuffer)
                } else if (isTokenIdentifier(tokenPeekInsideBrackets)) {
                    identifier(tokenBuffer)
                    if (isNextTokenOpeningParenthesis(tokenBuffer.peekNextToken())) { // Function call
                        tokenBuffer.removeLastReadTokenAndPutBackInTokenList()
                        callFunction(tokenBuffer)
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

        //private fun isFunctionDeclaration(token: Token): Boolean = functionDeclaration.contains(token.value)
    }
}