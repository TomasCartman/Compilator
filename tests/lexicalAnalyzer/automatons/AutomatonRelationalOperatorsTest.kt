package tests.lexicalAnalyzer.automatons

import lexicalAnalyzer.automatons.AutomatonRelationalOperators
import org.junit.jupiter.api.Assertions.*

internal class AutomatonRelationalOperatorsTest {
    private lateinit var automaton: AutomatonRelationalOperators

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
        automaton = AutomatonRelationalOperators()
    }

    @org.junit.jupiter.api.Test
    fun putNewString() {
        // Valid
        assertEquals(true, automaton.putNewString(">"))
        assertEquals(true, automaton.putNewString("!="))
        assertEquals(true, automaton.putNewString("=="))
        assertEquals(true, automaton.putNewString("<"))
        assertEquals(true, automaton.putNewString("="))
        assertEquals(true, automaton.putNewString("<="))
        assertEquals(true, automaton.putNewString(">="))

        // Invalid
        assertEquals(false, automaton.putNewString("a"))
        assertEquals(false, automaton.putNewString(" "))
        assertEquals(false, automaton.putNewString(">>"))
        assertEquals(false, automaton.putNewString("abc"))
        assertEquals(false, automaton.putNewString("(>"))
        assertEquals(false, automaton.putNewString("==="))
        assertEquals(false, automaton.putNewString("  "))
        assertEquals(false, automaton.putNewString("."))
        assertEquals(false, automaton.putNewString("\n"))
        assertEquals(false, automaton.putNewString("\\"))
        assertEquals(false, automaton.putNewString("<!"))
        assertEquals(false, automaton.putNewString("=!"))
        assertEquals(false, automaton.putNewString("<>"))
        assertEquals(false, automaton.putNewString("><"))
    }
}