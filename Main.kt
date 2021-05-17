import lexicalAnalyzer.FileReader
import lexicalAnalyzer.Lexer
import lexicalAnalyzer.Lexer2
import lexicalAnalyzer.LexerHelper
import parser.Parser
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


fun main() {
    //parser.main()
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

    readAllInputFiles()
}

private fun readAllInputFiles() {
    val path = Paths.get("input").toAbsolutePath()
    if(Files.exists(path) && Files.isDirectory(path)) {
        File(path.toString()).walk().forEach {
            val fileName = it.name
            if(fileName.startsWith("entrada") && fileName.endsWith(".txt")) {
                println("Reading file: $fileName")
                Parser(it.absolutePath).main(fileName)
            }
        }
    } else {
        throw Exception("You need a input directory to run the compiler")
    }
}
