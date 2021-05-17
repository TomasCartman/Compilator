package parser

import lexicalAnalyzer.Lexer
import parser.exceptions.ParserException
import parser.produtions.Init
import parser.utils.Utils.Companion.addParserException
import parser.utils.Utils.Companion.getErrors
import parser.utils.Utils.Companion.readTokens
import parser.utils.Utils.Companion.reset
import utils.ClassType
import utils.Token
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class Parser(private val filePath: String) {
    interface LexicalAnalyzerProvider {
        fun nextToken(): Token?

        fun returnToken(token: Token)
    }

    private var tokenBuffer = mutableListOf<Token>()
    private lateinit var lexer: Lexer

    fun main(fileName: String) {
        lexer = Lexer(filePath)
        tokenBuffer = lexer.tokenList()

        try {
            Init.init(tokenBuffer)
        } catch (e: ParserException) {
            println("Error line: ${e.line} - expected: ${e.expectedTokens} - got ${e.receivedToken?.value}")
        } catch (e: StackOverflowError) {
            println("StackOverflowError")
        }

        val fileNameNumber = fileName.substring(7, fileName.length -4)
        val outputFileName = "saida$fileNameNumber.txt"
        deleteFileIfExists(outputFileName)

        if (tokenBuffer.getErrors().isEmpty()) {
            tokenBuffer.readTokens().forEach {
                println("[${it.line}] - Value: ${it.value}")
                writeOnFile(fileName, "[${it.line}] - Value: ${it.value}\n")
            }

            println("\nSuccess parser compilation")
            writeOnFile(fileName, "\nSuccess parser compilation")
        } else {
            val errors = tokenBuffer.getErrors()
            val valid: MutableList<Token> = mutableListOf()
            valid.addAll(tokenBuffer.readTokens())
            valid.addAll(errors)

            valid.sortBy { token: Token -> token.line }
            valid.forEach {
                if (it.type.type == ClassType.UNKNOWN) {
                    println("[${it.line}] - Expected: ${it.expected} - Got ${it.value}")
                    writeOnFile(fileName, "[${it.line}] - Expected: ${it.expected} - Got ${it.value}\n")
                } else {
                    println("[${it.line}] - Value: ${it.value}")
                    writeOnFile(fileName, "[${it.line}] - Value: ${it.value}\n")
                }
            }
        }

        tokenBuffer.reset()
    }

    private fun writeOnFile(fileName: String, text: String) {
        val directoryPath = Paths.get("output")
        val fileNameNumber = fileName.substring(7, fileName.length -4)
        val outputFileName = "saida$fileNameNumber.txt"
        val filePath = Paths.get("output/$outputFileName")

        if(Files.exists(directoryPath) && Files.isDirectory(directoryPath)) {
            try {
                val fw = FileWriter(filePath.toFile(), true)
                fw.write(text)
                fw.close()
            } catch (e: IOException) {
                println("Error on writing file: $e")
            }
        } else {
            Files.createDirectory(directoryPath)
            try {
                val fw = FileWriter(filePath.toFile(), true)
                fw.write(text)
                fw.close()
            } catch (e: IOException) {
                println("Error on writing file: $e")
            }
        }
    }

    private fun deleteFileIfExists(fileName: String) {
        val filePath = Paths.get("output/$fileName")
        Files.deleteIfExists(filePath)
    }
}