package tests

import lexicalAnalyzer.AutomatonNumbers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AutomatonNumbersTest {
    private lateinit var automaton: AutomatonNumbers


    @BeforeEach
    fun setUp() {
        automaton = AutomatonNumbers()
    }

    @Test
    fun putNewString() {
        // Valid
        assertEquals(true, automaton.putNewString("1"))
        assertEquals(true, automaton.putNewString("32"))
        assertEquals(true, automaton.putNewString("12.2"))
        assertEquals(true, automaton.putNewString("43.76"))
        assertEquals(true, automaton.putNewString("3.33"))
        assertEquals(true, automaton.putNewString("12312.2313231313"))
        assertEquals(true, automaton.putNewString("12321321231321"))

        // Invalid
        assertEquals(false, automaton.putNewString("1."))
        assertEquals(false, automaton.putNewString(" "))
        assertEquals(false, automaton.putNewString("0."))
        assertEquals(false, automaton.putNewString(".2"))
        assertEquals(false, automaton.putNewString(".3232"))
        assertEquals(false, automaton.putNewString("23231."))
        assertEquals(false, automaton.putNewString("  "))
        assertEquals(false, automaton.putNewString("."))
        assertEquals(false, automaton.putNewString("\n"))
        assertEquals(false, automaton.putNewString("\\"))
        assertEquals(false, automaton.putNewString("books"))
        assertEquals(false, automaton.putNewString(" sds s"))
        assertEquals(false, automaton.putNewString("1. 2"))
        assertEquals(false, automaton.putNewString("3 .42"))
    }

    @Test
    fun generateToken() {
    }
}