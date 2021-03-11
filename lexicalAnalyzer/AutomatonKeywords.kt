package lexicalAnalyzer

import utils.ClassType
import utils.Token

class AutomatonKeywords : Automaton() {
    private val keywordsList = mutableListOf<String>()

    init {
        addKeywords()
    }

    override fun generateToken(): Token {
        val type = ClassType.createKeywordType()
        val token = Token(type)
        token.value = lexeme
        return  token
    }

    override fun testStringLexeme(): Boolean {
        return keywordsList.contains(lexeme)
    }

    private fun addKeywords() {
        keywordsList.add("var")
        keywordsList.add("const")
        keywordsList.add("typedef")
        keywordsList.add("struct")
        keywordsList.add("extends")
        keywordsList.add("procedure")
        keywordsList.add("function")
        keywordsList.add("start")
        keywordsList.add("return")
        keywordsList.add("if")
        keywordsList.add("else")
        keywordsList.add("then")
        keywordsList.add("while")
        keywordsList.add("read")
        keywordsList.add("print")
        keywordsList.add("int")
        keywordsList.add("real")
        keywordsList.add("boolean")
        keywordsList.add("string")
        keywordsList.add("true")
        keywordsList.add("false")
        keywordsList.add("global")
        keywordsList.add("local")
    }
}