package lexicalAnalyzer

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
    private val automatonString = AutomatonString()
    private val automatonComments = AutomatonComments()

    private lateinit var sourceCode: List<String>
    private var tokenList: MutableList<Token> = mutableListOf()
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
                if(fileName.startsWith("entrada5") && fileName.endsWith(".txt")) { // Put the startsWith to be 'entrada' instead of 'entrada3'
                    println("Reading file: $fileName")
                    sourceCode = readFileAsLinesUsingReadLines(it.absolutePath)
                    resetLexer()
                    checkLexeme()
                    deleteFileIfExists(fileName)
                    for(token in tokenList) {
                        writeOnFile(fileName, "[${token.line}] ${token.type.type} ${token.value}\n")
                    }
                }
            }
        } else {
            throw Exception("You need a input directory to run the compiler")
        }
    }

    private fun writeOnFile(fileName: String, text: String) {
        val directoryPath = Paths.get("output")
        val filePath = Paths.get("output/$fileName")
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
    }

    private fun checkLexeme() {
        var char = nextChar()

        while(char != null) {
            if(char != ' ') {
                var lexeme = char.toString()
                var token: Token? = null
                var isLexemeValid: Boolean
                var isLineComment = false
                var i = 0 // Variable to be sure that at least a lexeme with length of two was tested

                // Test the automatons until the last long valid lexeme
                do {

                    if(automatonKeywords.putNewString(lexeme)) {
                        isLexemeValid = true
                        token = automatonKeywords.generateToken()
                    } else if(automatonIdentifiers.putNewString(lexeme)) {
                        isLexemeValid = true
                        token = automatonIdentifiers.generateToken()
                    } else if(automatonRelationalOperators.putNewString(lexeme)) {
                        isLexemeValid = true
                        token = automatonRelationalOperators.generateToken()
                    } else if(automatonLogicalOperators.putNewString(lexeme)) {
                        isLexemeValid = true
                        token = automatonLogicalOperators.generateToken()
                    } else if(automatonArithmeticOperators.putNewString(lexeme)) {
                        isLexemeValid = true
                        token = automatonArithmeticOperators.generateToken()
                    } else if(automatonNumbers.putNewString(lexeme)) {
                        isLexemeValid = true
                        token = automatonNumbers.generateToken()
                    } else if(automatonDelimiters.putNewString(lexeme)) {
                        isLexemeValid = true
                        token = automatonDelimiters.generateToken()
                    } else if(automatonString.putNewString(lexeme)) { // Change the way this automaton works
                        do {
                            val newChar = nextCharLookahead()
                            token = automatonString.generateToken()
                            lexeme += newChar
                        } while (automatonString.putNewString(lexeme) && newChar != null)
                        break
                    } else if(automatonComments.putNewString(lexeme)) {
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
                                //    '*/' before opening comment, error
                            }
                        }
                        break
                    } // else if, test others automatons
                    else {
                        isLexemeValid = false
                    }

                    val lookahead = nextCharLookahead() ?: break
                    if(isLexemeValid && (!lookahead.isWhitespace())) {
                        lexeme += lookahead.toString()
                        isLexemeValid = true
                        //i = 0
                    } else {
                        i = 1
                        isLexemeValid = false
                    }

                } while(isLexemeValid)

                if(token != null && !isLineComment) {
                    token.line = line + 1
                    tokenList.add(token)
                    jumpChar(token.value.length - 1)
                } else {
                    if(!isLineComment && lexeme.isNotBlank()) println(lexeme)
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
        do {
            val newChar = nextChar()
            val newLookaheadChar = nextCharLookahead()
            if(newChar == null) break // Indicates a error here
        } while(newChar != '*' || newLookaheadChar != '/')
        jumpChar(1)
    }
}

/*
Cadeia de caracters: Definir symbols como no AutomatonKeywords, e dois estados para dentro dos parenteses para '\' e '"'
 */

/*
Delimetadores: ao testar o ponto '.', usar um strip() para garantir que não há espaços em branco entre o ponto
(na real, acho que nem vai funcionar)
 */
