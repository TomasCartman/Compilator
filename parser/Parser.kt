package parser

import lexicalAnalyzer.Lexer
import utils.Token

class Parser {
    interface LexicalAnalyzerProvider {
        fun nextToken(): Token?
    }

    private val tokenBuffer = mutableListOf<Token>()
    private lateinit var lexer: Lexer

    fun main() {
        lexer = Lexer("./input/entrada10.txt")
        init()
    }

    private fun init() {
        var token = lexer.nextToken()
        if(token != null && token.value == "procedure") {
           token = lexer.nextToken()
           if(token != null && token.value == "start") {
               token = lexer.nextToken()
               if(token != null && token.value == "{") {
                   start()
                   token = lexer.nextToken()
                   if(token != null && token.value == "}") {
                       // The program has ended
                   }
               }
           }
        }
    }

    private fun start() {
        program()
    }

    private fun program() {
        statement()
    }

    private fun statement() {
        var token = lexer.nextToken()
        if(token != null && token.value == "{") {
            token = lexer.nextToken()
            if (token != null && token.value == "}") {
                return
            } else {
                //statementList()
                token = lexer.nextToken()
                if(token != null && token.value == "}") {
                    return
                }
            }
        } else {
            // Calls all the other things
        }
    }
}