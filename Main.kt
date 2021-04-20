import lexicalAnalyzer.FileReader
import lexicalAnalyzer.Lexer
import lexicalAnalyzer.Lexer2
import lexicalAnalyzer.LexerHelper

val lexer = Lexer()

fun main() {
    //lexer.main()
    val res = FileReader.readFileAsTextUsingInputStream("./input/entrada02.txt")
    val lexer2 = Lexer2(res)
    /*
    do {
        val c = lexerHelper.getNextLookaheadChar()
        println("char: $c -> int: ${c?.toInt()}")
    }while (c != null)

     */
    println(lexer2.getNextToken())
}
