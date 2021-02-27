package analisadorLexico

import analisadorLexico.AutomatonRelationalOperators
import java.io.BufferedReader
import java.io.File

class Lexer() {
    private val automatonRelationalOperators = AutomatonRelationalOperators()
    lateinit var sourceCode: List<String>
    var line = 0
    var position = 0
    var lookahead = 0


    fun main() {
        sourceCode = readFileAsLinesUsingReadLines("./input/entrada1.txt")

        var char = nextChar()
        while(char != null) {
            val result = automatonRelationalOperators.putNewChar(char)
            print("$line : $position ")
            print("$result ")
            println(char)
            if(!result) automatonRelationalOperators.resetAutomaton()
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
            // Checks if is an empty line (this include spaces if there are just them in the line)
            if(sourceCode[line].isNotEmpty()) {
                val char = sourceCode[line][position]
                position += 1
                return char
            } else {
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
}

