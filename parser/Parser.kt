package parser

import lexicalAnalyzer.Lexer
import parser.exceptions.ParserException
import parser.produtions.Init
import parser.utils.Utils.Companion.getErrors
import parser.utils.Utils.Companion.readTokens
import utils.ClassType
import utils.Token

class Parser {
    interface LexicalAnalyzerProvider {
        fun nextToken(): Token?

        fun returnToken(token: Token)
    }

    private var tokenBuffer = mutableListOf<Token>()
    private lateinit var lexer: Lexer
    private val type = listOf("boolean", "int", "real", "string")

    fun main() {
        lexer = Lexer("./input/entrada12.txt")
        tokenBuffer = lexer.tokenList()

        try {
            Init.init(tokenBuffer)

        } catch (e: ParserException) {
            println("Error line: ${e.line} - expected: ${e.expectedTokens} - got ${e.receivedToken?.value}")
        } catch (e: StackOverflowError) {
            println("StackOverflowError")
        }



        if (tokenBuffer.getErrors().isEmpty()) {
            tokenBuffer.readTokens().forEach {
                println("Value: ${it.value} - line: ${it.line}")
            }

            println("\nSuccess parser compilation")
        } else {
            val errors = tokenBuffer.getErrors()
            val valids: MutableList<Token> = mutableListOf()
            valids.addAll(tokenBuffer.readTokens())
            valids.addAll(errors)

            valids.sortBy { token: Token -> token.line }
            valids.forEach {
                if (it.type.type == ClassType.UNKNOWN) {
                    println("[${it.line}] - Expected: ${it.expected} - Got ${it.value} ")
                } else {
                    println("[${it.line}] - Value: ${it.value}")
                }
            }
        }
    }
}