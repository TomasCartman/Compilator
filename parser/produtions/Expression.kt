package parser.produtions

import parser.exceptions.ParserException
import parser.produtions.ArithmeticOperator.Companion.division
import parser.produtions.ArithmeticOperator.Companion.isNextTokenDivisionSymbol
import parser.produtions.ArithmeticOperator.Companion.isNextTokenMinusSymbol
import parser.produtions.LogicalOperators.Companion.isNextTokenLogicalAndSymbol
import parser.utils.Utils.Companion.peekNextToken
import parser.produtions.LogicalOperators.Companion.isNextTokenLogicalOrSymbol
import parser.produtions.LogicalOperators.Companion.logicalAnd
import parser.produtions.LogicalOperators.Companion.logicalOr
import parser.produtions.RelationalOperators.Companion.equal
import parser.produtions.RelationalOperators.Companion.greaterThan
import parser.produtions.RelationalOperators.Companion.greaterThanOrEqual
import parser.produtions.RelationalOperators.Companion.isNextTokenEqualSymbol
import parser.produtions.RelationalOperators.Companion.isNextTokenGreaterThanOrEqualSymbol
import parser.produtions.RelationalOperators.Companion.isNextTokenGreaterThanSymbol
import parser.produtions.RelationalOperators.Companion.isNextTokenLessThanOrEqualSymbol
import parser.produtions.RelationalOperators.Companion.isNextTokenLessThanSymbol
import parser.produtions.RelationalOperators.Companion.isNextTokenNotEqualSymbol
import parser.produtions.RelationalOperators.Companion.lessThan
import parser.produtions.RelationalOperators.Companion.lessThanOrEqual
import parser.produtions.RelationalOperators.Companion.notEqual
import parser.produtions.ArithmeticOperator.Companion.isNextTokenPlusSymbol
import parser.produtions.ArithmeticOperator.Companion.isNextTokenTimesSymbol
import parser.produtions.ArithmeticOperator.Companion.minus
import parser.produtions.ArithmeticOperator.Companion.plus
import parser.produtions.ArithmeticOperator.Companion.times
import parser.produtions.Delimeters.Companion.closingParenthesis
import parser.produtions.LogicalOperators.Companion.isNextTokenLogicalNotSymbol
import parser.produtions.Delimeters.Companion.isNextTokenOpeningParenthesis
import parser.produtions.Delimeters.Companion.openingParenthesis
import parser.produtions.VarDeclaration.Companion.isTokenPrimary
import parser.produtions.VarDeclaration.Companion.primaryStringListName
import parser.utils.Utils.Companion.nextToken
import utils.Token

class Expression {
    companion object {
        fun expression(tokenBuffer: MutableList<Token>) {
            orExpression(tokenBuffer)
        }

        private fun orExpression(tokenBuffer: MutableList<Token>) {
            andExpression(tokenBuffer)
            if(isNextTokenLogicalOrSymbol(tokenBuffer.peekNextToken())) {
                logicalOr(tokenBuffer)
                orExpression(tokenBuffer)
            }
        }

        private fun andExpression(tokenBuffer: MutableList<Token>) {
            equalityExpression(tokenBuffer)
            if(isNextTokenLogicalAndSymbol(tokenBuffer.peekNextToken())) {
                logicalAnd(tokenBuffer)
                andExpression(tokenBuffer)
            }
        }

        private fun equalityExpression(tokenBuffer: MutableList<Token>) {
            compareExpression(tokenBuffer)
            val tokenPeek = tokenBuffer.peekNextToken()
            if(isNextTokenEqualSymbol(tokenPeek)) {
                equal(tokenBuffer)
                equalityExpression(tokenBuffer)
            } else if(isNextTokenNotEqualSymbol(tokenPeek)) {
                notEqual(tokenBuffer)
                equalityExpression(tokenBuffer)
            }
        }

        private fun compareExpression(tokenBuffer: MutableList<Token>) {
            addExpression(tokenBuffer)
            val tokenPeek = tokenBuffer.peekNextToken()
            when {
                isNextTokenLessThanSymbol(tokenPeek) -> {
                    lessThan(tokenBuffer)
                    compareExpression(tokenBuffer)
                }
                isNextTokenGreaterThanSymbol(tokenPeek) -> {
                    greaterThan(tokenBuffer)
                    compareExpression(tokenBuffer)
                }
                isNextTokenLessThanOrEqualSymbol(tokenPeek) -> {
                    lessThanOrEqual(tokenBuffer)
                    compareExpression(tokenBuffer)
                }
                isNextTokenGreaterThanOrEqualSymbol(tokenPeek) -> {
                    greaterThanOrEqual(tokenBuffer)
                    compareExpression(tokenBuffer)
                }
            }
        }

        private fun addExpression(tokenBuffer: MutableList<Token>) {
            multiplicationExpression(tokenBuffer)
            val tokenPeek = tokenBuffer.peekNextToken()
            if(isNextTokenPlusSymbol(tokenPeek)) {
                plus(tokenBuffer)
                addExpression(tokenBuffer)
            } else if(isNextTokenMinusSymbol(tokenPeek)) {
                minus(tokenBuffer)
                addExpression(tokenBuffer)
            }
        }

        private fun multiplicationExpression(tokenBuffer: MutableList<Token>) {
            unaryExpression(tokenBuffer)
            val tokenPeek = tokenBuffer.peekNextToken()
            if(isNextTokenTimesSymbol(tokenPeek)) {
                times(tokenBuffer)
                multiplicationExpression(tokenBuffer)
            } else if(isNextTokenDivisionSymbol(tokenPeek)) {
                division(tokenBuffer)
                multiplicationExpression(tokenBuffer)
            }
        }

        private fun unaryExpression(tokenBuffer: MutableList<Token>) {
            if (isNextTokenLogicalNotSymbol(tokenBuffer.peekNextToken())) {
                unaryExpression(tokenBuffer)
            } else {
                primaryExpression(tokenBuffer)
            }
        }

        private fun primaryExpression(tokenBuffer: MutableList<Token>) {
            val tokenPeek = tokenBuffer.peekNextToken()
            when {
                isNextTokenOpeningParenthesis(tokenPeek) -> {
                    openingParenthesis(tokenBuffer)
                    expression(tokenBuffer)
                    closingParenthesis(tokenBuffer)
                }
                isTokenPrimary(tokenPeek) -> {
                    tokenBuffer.nextToken() // Consume Primary
                }
                else -> throw ParserException(tokenPeek.line, tokenPeek, primaryStringListName)
            }
        }
    }
}