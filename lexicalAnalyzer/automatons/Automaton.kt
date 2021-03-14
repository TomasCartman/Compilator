package lexicalAnalyzer.automatons

import utils.Token

abstract class Automaton {
    protected var lexeme: String = ""
    protected var state: Int = 0

    fun putNewString(s: String): Boolean {
        resetAutomaton()
        lexeme = s

        return testStringLexeme()
    }

    abstract fun generateToken(): Token

    private fun resetAutomaton() {
        state = 0
    }

    protected abstract fun testStringLexeme(): Boolean
}