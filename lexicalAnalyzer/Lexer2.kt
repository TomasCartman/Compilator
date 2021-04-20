package lexicalAnalyzer

import lexicalAnalyzer.automatons.*
import utils.Token

class Lexer2(private val sourceCode: String) {
    private val automatonRelationalOperators = AutomatonRelationalOperators()
    private val automatonNumbers = AutomatonNumbers()
    private val automatonKeywords = AutomatonKeywords()
    private val automatonArithmeticOperators = AutomatonArithmeticOperators()
    private val automatonIdentifiers = AutomatonIdentifiers()
    private val automatonLogicalOperators = AutomatonLogicalOperators()
    private val automatonDelimiters = AutomatonDelimiters()
    private val automatonComments = AutomatonComments()
    private val lexerHelper = LexerHelper(sourceCode)

    fun getNextToken(): Token? {
        var char = lexerHelper.getNextChar()
        var lookaheadChar = lexerHelper.getNextLookaheadChar()
        var lexeme = char.toString()
        var token: Token? = null
        while (char != null && !char.isWhitespace()) {
            if (char.toInt() == AsciiTable.Slash.decValue &&
                lookaheadChar != null && lookaheadChar.toInt() == AsciiTable.Slash.decValue) {

                lexerHelper.jumpLine()
            }

            token = testAutomaton(lexeme)


            char = lexerHelper.getNextChar()
            lexeme += char
        }
        println(token)
        return token
    }

    private fun testAutomaton(lexeme: String): Token? {
        return when {
            automatonKeywords.putNewString(lexeme) -> automatonKeywords.generateToken()
            automatonIdentifiers.putNewString(lexeme) -> automatonIdentifiers.generateToken()
            automatonRelationalOperators.putNewString(lexeme) -> automatonRelationalOperators.generateToken()
            automatonLogicalOperators.putNewString(lexeme) -> automatonLogicalOperators.generateToken()
            automatonArithmeticOperators.putNewString(lexeme) -> automatonArithmeticOperators.generateToken()
            automatonNumbers.putNewString(lexeme) -> automatonNumbers.generateToken()
            automatonDelimiters.putNewString(lexeme) -> automatonDelimiters.generateToken()
            else -> null
        }
    }

    private enum class AsciiTable(val decValue: Int) {
        Space(32),
        Slash(47)
    }
}