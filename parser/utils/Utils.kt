package parser.utils

import parser.exceptions.NextTokenNullException
import parser.exceptions.ParserException
import parser.utils.Utils.Companion.nextToken
import utils.ClassType
import utils.Token

class Utils {
    companion object {
        var readTokens = mutableListOf<Token>()
        var errorTokens = mutableListOf<Token>()

        fun MutableList<Token>.hasNextToken(): Boolean = this.isNotEmpty()

        fun MutableList<Token>.nextToken(): Token {
            if(this.hasNextToken()) {
                val token = this[0]
                this.removeAt(0)
                readTokens.add(token)
                return token
            }
            throw NextTokenNullException()
        }

        fun MutableList<Token>.skipToken(): Token {
            if(this.hasNextToken()) {
                val token = this[0]
                this.removeAt(0)
                //readTokens.add(token)
                return token
            }
            throw NextTokenNullException()
        }

        fun MutableList<Token>.putTokenBack(token: Token) {
            this.add(0, token)
        }

        fun MutableList<Token>.readTokens(): List<Token> = readTokens

        fun MutableList<Token>.lastValidToken(): Token? {
            return try {
                readTokens.last()
            } catch (e : NoSuchElementException) {
                null
            }
        }

        fun MutableList<Token>.removeLastReadTokenAndPutBackInTokenList() {
            val token = readTokens.removeAt(readTokens.lastIndex)
            this.add(0, token)
        }

        fun MutableList<Token>.peekNextToken(): Token {
            if(this.hasNextToken()) {
                return this[0]
            }
            throw NextTokenNullException()
        }

        fun MutableList<Token>.peekNextTokenOrNull(): Token? {
            return if(this.hasNextToken()) {
                this[0]
            } else
                null
        }

        fun MutableList<Token>.addParserException(e: ParserException) {
            val receivedTokenLine = e.receivedToken?.line ?: 0
            val receivedTokenValue = e.receivedToken?.value ?: ""
            val token = Token(ClassType.createUnknownErrorType(), receivedTokenValue, receivedTokenLine, e.expectedTokens)
            errorTokens.add(token)
        }

        fun MutableList<Token>.getErrors(): List<Token> = errorTokens


        fun throwParserError(expected: List<String>, tokenBuffer: MutableList<Token>) {
            val lastValidToken = tokenBuffer.lastValidToken()
            val actualToken = tokenBuffer.peekNextTokenOrNull()

            if (actualToken != null && lastValidToken != null) {
                val actualTokenCopy = actualToken.copy(line = lastValidToken.line)
                throw ParserException(actualTokenCopy.line, actualTokenCopy, expected)
            } else if(lastValidToken != null) {
                val lastValidTokenCopy = lastValidToken.copy(value = "EOF")
                throw ParserException(lastValidTokenCopy.line, lastValidTokenCopy, expected)
            } else {
                throw ParserException(0, null, expected)
            }
        }
    }
}