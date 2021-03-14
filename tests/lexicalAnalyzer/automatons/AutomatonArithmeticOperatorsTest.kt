package tests.lexicalAnalyzer.automatons

import lexicalAnalyzer.automatons.AutomatonArithmeticOperators
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AutomatonArithmeticOperatorsTest {
    private lateinit var automaton: AutomatonArithmeticOperators


    @BeforeEach
    fun setUp() {
        automaton = AutomatonArithmeticOperators()
    }

    @Test
    fun putNewString() {
        // Valid
        assertEquals(true, automaton.putNewString("+"))
        assertEquals(true, automaton.putNewString("++"))
        assertEquals(true, automaton.putNewString("-"))
        assertEquals(true, automaton.putNewString("--"))
        assertEquals(true, automaton.putNewString("*"))
        assertEquals(true, automaton.putNewString("/"))

        // Invalid
        assertEquals(false, automaton.putNewString("ads"))
        assertEquals(false, automaton.putNewString(" "))
        assertEquals(false, automaton.putNewString("+++"))
        assertEquals(false, automaton.putNewString("---"))
        assertEquals(false, automaton.putNewString("**"))
        assertEquals(false, automaton.putNewString("==="))
        assertEquals(false, automaton.putNewString("  "))
        assertEquals(false, automaton.putNewString("."))
        assertEquals(false, automaton.putNewString("\n"))
        assertEquals(false, automaton.putNewString("\\"))
        assertEquals(false, automaton.putNewString("//"))
        assertEquals(false, automaton.putNewString("/*"))
        assertEquals(false, automaton.putNewString("*/"))
        assertEquals(false, automaton.putNewString("+/"))
        assertEquals(false, automaton.putNewString("/-"))
        assertEquals(false, automaton.putNewString("+-"))
    }
}