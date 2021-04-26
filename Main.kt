import lexicalAnalyzer.FileReader
import lexicalAnalyzer.Lexer
import lexicalAnalyzer.Lexer2
import lexicalAnalyzer.LexerHelper
import parser.Parser

val parser = Parser()

fun main() {
    parser.main()
    //lexer.main("./input/entrada02.txt")
    /*
    val res = FileReader.readFileAsTextUsingInputStream("./input/entrada02.txt")
    val lexer2 = Lexer2(res)

    do {
        val c = lexerHelper.getNextLookaheadChar()
        println("char: $c -> int: ${c?.toInt()}")
    }while (c != null)


    var token = lexer2.getNextToken()
    while(token != null) {
        println("class: ${token.type.type} value: ${token.value} line: ${token.line}")
        token = lexer2.getNextToken()
    }
    */
}
