package tests

import lexicalAnalyzer.AutomatonString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AutomatonStringTest {
    private lateinit var automaton: AutomatonString

    @BeforeEach
    fun setUp() {
        automaton = AutomatonString()
    }

    @Test
    fun putNewString() {
        // Valid
        /*
        var string = "test"
        assertEquals(true, automaton.putNewString(string))
        string = "test  dsda dae  "
        assertEquals(true, automaton.putNewString(string))
        string = "ewrre \" dsaddada"
        assertEquals(true, automaton.putNewString(string))

         */
        /*
        // Invalid
        assertEquals(false, automaton.putNewString(""))
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

         */
    }

    @Test
    fun generateToken() {
    }
}