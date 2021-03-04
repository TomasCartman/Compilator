package lexicalAnalyzer

import utils.Token
import java.io.File

class Lexer {
    private val automatonRelationalOperators = AutomatonRelationalOperators()
    private val automatonNumbers = AutomatonNumbers()
    private val automatonKeywords = AutomatonKeywords()
    private val automatonArithmeticOperators = AutomatonArithmeticOperators()
    private val automatonIdentifiers = AutomatonIdentifiers()
    private val automatonLogicalOperators = AutomatonLogicalOperators()
    private val automatonString = AutomatonString()
    private val automatonComments = AutomatonComments()
    private lateinit var sourceCode: List<String>
    private var line = 0
    private var position = 0
    private var lookahead = 0

    fun main() {
        sourceCode = readFileAsLinesUsingReadLines("./input/entrada3.txt")

        var char = nextChar()

        while(char != null) {
            if(char != ' ') {
                var lexeme = char.toString()
                var token: Token? = null
                var isLexemeValid: Boolean
                var isLineComment = false
                var isString = false
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
                    } else if(automatonString.putNewString(lexeme)) {
                        isLexemeValid = true
                        isString = true
                        do {
                            val newChar = nextCharLookahead()
                            token = automatonString.generateToken()
                            lexeme += newChar
                        } while (automatonString.putNewString(lexeme))
                        break
                    } else if(automatonComments.putNewString(lexeme)) {
                        token = automatonComments.generateToken()
                        when(token.value) {
                            "//" -> {
                                isLineComment = true
                                line += 1
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
                    if(isLexemeValid || (lookahead != ' ' && i <= 0)) {
                        lexeme += lookahead.toString()
                        isLexemeValid = true
                        i = 0
                    } else {
                        i += 1
                    }

                } while(isLexemeValid)

                if(token != null && !isLineComment) {
                    println("{" + token.type.type + ", " + token.value + "}")

                    //if(!isString) {
                        jumpChar(token.value.length - 1)
                    //}
                }
            }

            char = nextChar()
        }
        //println(sourceCode)
        /*
        for(page in sourceCode) {
            println(page)
        }
         */
    }

    private fun readFileAsLinesUsingReadLines(fileName: String): List<String>
            = File(fileName).readLines()

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
            if(newChar == null) break
        } while(newChar != '*' || newLookaheadChar != '/')
        jumpChar(1)
    }
}





/*
Cadeia de caracters: Definir symbols como no AutomatonKeywords, e dois estados para dentro dos parenteses para '\' e '"'
 */
