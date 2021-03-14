package lexicalAnalyzer

import lexicalAnalyzer.automatons.*
import utils.ClassType
import utils.Token
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class Lexer {
    private val automatonRelationalOperators = AutomatonRelationalOperators()
    private val automatonNumbers = AutomatonNumbers()
    private val automatonKeywords = AutomatonKeywords()
    private val automatonArithmeticOperators = AutomatonArithmeticOperators()
    private val automatonIdentifiers = AutomatonIdentifiers()
    private val automatonLogicalOperators = AutomatonLogicalOperators()
    private val automatonDelimiters = AutomatonDelimiters()
    private val automatonComments = AutomatonComments()

    private lateinit var sourceCode: List<String>
    private var tokenList: MutableList<Token> = mutableListOf()
    private var errorList: MutableList<Token> = mutableListOf()
    private var line = 0
    private var position = 0
    private var lookahead = 0

    fun main() {
        readAllInputFiles()
    }

    private fun readFileAsLinesUsingReadLines(fileName: String): List<String>
            = File(fileName).readLines()

    private fun readAllInputFiles() {
        val path = Paths.get("input").toAbsolutePath()
        if(Files.exists(path) && Files.isDirectory(path)) {
            File(path.toString()).walk().forEach {
                val fileName = it.name
                if(fileName.startsWith("entrada") && fileName.endsWith(".txt")) {
                    println("Reading file: $fileName")
                    sourceCode = readFileAsLinesUsingReadLines(it.absolutePath)
                    resetLexer()
                    checkLexeme()
                    val fileNameNumber = fileName.substring(7, fileName.length -4)
                    val outputFileName = "saida$fileNameNumber.txt"
                    deleteFileIfExists(outputFileName)
                    for(token in tokenList) {
                        writeOnFile(fileName, "[${token.line}] ${token.type.type} ${token.value}\n")
                    }
                    writeOnFile(fileName, "\n")
                    errorList.forEach { errorToken ->
                        writeOnFile(fileName, "${errorToken.line} ${errorToken.type.type} ${errorToken.value}\n")
                    }
                }
            }
        } else {
            throw Exception("You need a input directory to run the compiler")
        }
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

    private fun resetLexer() {
        line = 0
        position = 0
        lookahead = 0
        tokenList = mutableListOf()
        errorList = mutableListOf()
    }

    private fun checkLexeme() {
        var char = nextChar()

        while(char != null) {
            if(!char.isWhitespace()) {
                var lexeme = char.toString()
                var token: Token? = null
                var isLexemeValid = true
                var isLineComment = false
                var isString = false

                // Test the automatons until the last long valid lexeme
                do {
                    when {
                        automatonKeywords.putNewString(lexeme) -> token = automatonKeywords.generateToken()
                        automatonIdentifiers.putNewString(lexeme) -> token = automatonIdentifiers.generateToken()
                        automatonRelationalOperators.putNewString(lexeme) -> token = automatonRelationalOperators.generateToken()
                        automatonLogicalOperators.putNewString(lexeme) -> token = automatonLogicalOperators.generateToken()
                        automatonArithmeticOperators.putNewString(lexeme) -> token = automatonArithmeticOperators.generateToken()
                        automatonNumbers.putNewString(lexeme) -> token = automatonNumbers.generateToken()
                        automatonDelimiters.putNewString(lexeme) -> token = automatonDelimiters.generateToken()

                        lexeme == "\"" -> {
                            isString = true
                            stringIdentifier(lexeme)
                        }

                        automatonComments.putNewString(lexeme) -> {
                            token = automatonComments.generateToken()
                            when(token.value) {
                                "//" -> {
                                    isLineComment = true
                                    line += 1
                                    position = 0
                                    lookahead = 0
                                }
                                "/*" -> {
                                    isLineComment = true
                                    skipComments()
                                }
                                else -> {
                                    token = null
                                    //    '*/' before opening comment, error
                                }
                            }
                        }
                        else -> isLexemeValid = false
                    }

                    val lookahead = nextCharLookahead() ?: break
                    if(isLexemeValid || (!lookahead.isWhitespace()) && lexeme.length <= 2 ) {
                        lexeme += lookahead.toString()
                        isLexemeValid = true
                    } else isLexemeValid = false

                } while(isLexemeValid)

                if(token != null && !isLineComment) { // Valid token
                    token.line = line + 1
                    tokenList.add(token)
                    jumpChar(token.value.length - 1)
                } else { // Error token
                    if(!isLineComment && !isString && lexeme.isNotBlank()) {
                        if(lexeme.length == 1) {
                            val type = if(lexeme.any { it == '|' || it =='&' }) ClassType.createBadFormattedOperatorErrorType()
                                    else ClassType.createInvalidSymbolErrorType()

                            val errorToken = Token(type, lexeme, line + 1)
                            errorList.add(errorToken)
                        } else {
                            val type = ClassType.createUnknownErrorType()
                            val errorToken = Token(type, lexeme, line + 1)
                            errorList.add(errorToken)
                            jumpChar(1)
                        }
                    }
                }
            }

            char = nextChar()
        }
    }

    private fun nextChar(): Char? {
        // It has one or more lines to read and the actual line it's not at the end yet
        return if(sourceCode.size > line && sourceCode[line].length > position) {
            val char = sourceCode[line][position]
            position += 1
            lookahead = position
            char
        }
        // The actual line is valid, but the position variable is a the end of line
        else if(sourceCode.size > line + 1 && sourceCode[line].length <= position) {
            line += 1
            position = 0
            lookahead = position
            // Checks if is not an empty line (this include spaces if there are just them in the line)
            if(sourceCode[line].isNotEmpty()) {
                val char = sourceCode[line][position]
                position += 1
                lookahead = position
                return char
            } else {
                // While there is just empty lines or lines with spaces, skip to next line
                while (sourceCode.size > line + 1 && sourceCode[line].isEmpty()) {
                    line += 1
                }
                return if(sourceCode.size > line) {
                    val char = sourceCode[line][position]
                    position += 1
                    lookahead = position
                    char
                } else {
                    null
                }
            }
        }
        // There's no more line to read
        else {
            null
        }
    }

    private fun nextCharLookahead(): Char? {
        return if(sourceCode.size > line && sourceCode[line].length > lookahead) {
            val char = sourceCode[line][lookahead]
            lookahead += 1
            char
        } else {
            null
        }
    }

    private fun jumpChar(jumpSize: Int) {
        var i = jumpSize
        while(i > 0) {
            nextChar()
            i -= 1
        }
    }

    private fun skipComments() {
        jumpChar(1)
        var lexeme = "/*"
        do {
            val newChar = nextChar()
            val newLookaheadChar = nextCharLookahead()
            if(newChar == null) {
                val type = ClassType.createBadFormattedCommentErrorType()
                val token = Token(type = type, value = lexeme ,line = line + 1)
                errorList.add(token)
                break
            }
            lexeme += newChar.toString()
        } while(newChar != '*' || newLookaheadChar != '/')
        jumpChar(1)
    }

    private fun stringIdentifier(lexemeCode: String) {
        var validString = true
        var lexeme = lexemeCode
        do {
            val newChar = nextChar()
            lexeme += newChar.toString()
            val newLookahead = nextCharLookahead()
            if(newLookahead == null && newChar != '"') {
                val type = ClassType.createBadFormattedStringErrorType()
                val token = Token(type, lexeme, line + 1)
                errorList.add(token)
                validString = false
                break
            } else if(newChar == '\\' && newLookahead == '"') {
                lexeme += newLookahead.toString()
                jumpChar(1)
            }
        } while (newChar != '"')
        if(validString) {
            val type = ClassType.createStringType()
            val token = Token(type, lexeme, line + 1)
            tokenList.add(token)
        }
    }
}