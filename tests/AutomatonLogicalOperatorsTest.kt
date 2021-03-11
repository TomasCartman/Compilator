package tests

import lexicalAnalyzer.AutomatonLogicalOperators
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AutomatonLogicalOperatorsTest {
    private lateinit var automaton: AutomatonLogicalOperators

    @BeforeEach
    fun setUp() {
        automaton = AutomatonLogicalOperators()
    }

    @Test
    fun putNewString() {
        // Valid
        assertEquals(true, automaton.putNewString("&&"))
        assertEquals(true, automaton.putNewString("||"))
        assertEquals(true, automaton.putNewString("!"))

        // Invalid
        assertEquals(false, automaton.putNewString("&"))
        assertEquals(false, automaton.putNewString(" "))
        assertEquals(false, automaton.putNewString("|"))
        assertEquals(false, automaton.putNewString("!!"))
        assertEquals(false, automaton.putNewString("&&&"))
        assertEquals(false, automaton.putNewString("==="))
        assertEquals(false, automaton.putNewString("  "))
        assertEquals(false, automaton.putNewString("."))
        assertEquals(false, automaton.putNewString("\n"))
        assertEquals(false, automaton.putNewString("\\"))
        assertEquals(false, automaton.putNewString("|||"))
        assertEquals(false, automaton.putNewString("cook"))
        assertEquals(false, automaton.putNewString("12312"))
        assertEquals(false, automaton.putNewString("var"))
        assertEquals(false, automaton.putNewString("1.1"))
        assertEquals(false, automaton.putNewString("+"))
        assertEquals(false, automaton.putNewString(">="))
    }
}