package analisadorLexico

import analisadorLexico.AutomatonRelationalOperators
import java.io.File

class Lexer() {
    lateinit var sourceCode: List<String>

    fun main() {
        sourceCode = readFileAsLinesUsingReadLines("./input/entrada1.txt")

    }

    private fun readFileAsLinesUsingReadLines(fileName: String): List<String>
            = File(fileName).readLines()
    
    companion object {
        fun a () {
            val a : Array<Int> = emptyArray()
            println("s")
        }
    }
}

