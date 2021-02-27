package analisadorLexico

import java.io.File

class Lexer {
    private val automatonRelationalOperators = AutomatonRelationalOperators()
    private lateinit var sourceCode: List<String>
    private var line = 0
    private var position = 0
    private var lookahead = 0

    fun main() {
        sourceCode = readFileAsLinesUsingReadLines("./input/entrada1.txt")

        var char = nextChar()
        while(char != null) {
            if(char != ' ') {
                val lookaheadChar = nextCharLookahead()
                val result = automatonRelationalOperators.putNewChar(char)

                if(lookaheadChar != null) {
                    val newResult = automatonRelationalOperators.putNewChar(lookaheadChar)
                    // First char and lookahead are valid -> token with two chars
                    if(result && newResult) {
                        position += 1
                        println("<relop, [$char, $lookaheadChar]>")
                    }
                    // First char is valid but lookahead is not -> token with one char
                    else if(result && !newResult) {
                        println("<relop, [$char]>")
                    }
                    // First char is invalid but lookahead is valid -> token "!="
                    else if(newResult && !result) {
                        position += 1
                        println("<relop, [$char, $lookaheadChar]>")
                    }
                    // First char and lookahead are invalid -> no token
                    else {
                        automatonRelationalOperators.resetAutomaton()
                    }
                } else {
                    // First char is valid but lookahead is not -> token with one char
                    if(result) println("<relop, [$char]>") // Create a token
                    else automatonRelationalOperators.resetAutomaton() // testing the lookahead of end of line, should reset the automaton
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
            char
        }
        // The actual line is valid, but the position variable is a the end of line
        else if(sourceCode.size > line + 1 && sourceCode[line].length <= position) {
            line += 1
            position = 0
            // Checks if is not an empty line (this include spaces if there are just them in the line)
            if(sourceCode[line].isNotEmpty()) {
                val char = sourceCode[line][position]
                position += 1
                return char
            } else {
                // While there is just empty lines or lines with spaces, skip to next line
                while (sourceCode.size > line + 1 && sourceCode[line].isEmpty()) {
                    line += 1
                }
                return if(sourceCode.size > line) {
                    val char = sourceCode[line][position]
                    position += 1
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
        lookahead = position
        return if(sourceCode.size > line && sourceCode[line].length > lookahead) {
            sourceCode[line][lookahead]
        } else {
            null
        }
    }
}

