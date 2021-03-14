package lexicalAnalyzer.automatons

import utils.ClassType
import utils.Token

class AutomatonKeywords : Automaton() {
    private val keywordsList = listOf("var", "const", "typedef", "struct", "extends", "procedure", "function", "start",
                            "return", "if", "else", "then", "while", "read", "print", "int", "real", "boolean",
                            "string", "true", "false", "global", "local")

    override fun generateToken(): Token {
        val type = ClassType.createKeywordType()
        val token = Token(type)
        token.value = lexeme
        return  token
    }

    override fun testStringLexeme(): Boolean {
        return keywordsList.contains(lexeme)
    }
}