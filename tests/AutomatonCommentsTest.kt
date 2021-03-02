package tests

import lexicalAnalyzer.AutomatonComments
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AutomatonCommentsTest {
    private lateinit var automaton: AutomatonComments

    @BeforeEach
    fun setUp() {
        automaton = AutomatonComments()
    }

    @Test
    fun putNewString() {
        // Valid
        assertEquals(true, automaton.putNewString("/*"))
        assertEquals(true, automaton.putNewString("//"))
        assertEquals(true, automaton.putNewString("*/"))

        // Invalid
        assertEquals(false, automaton.putNewString("///"))
        assertEquals(false, automaton.putNewString(" "))
        assertEquals(false, automaton.putNewString("**"))
        assertEquals(false, automaton.putNewString("==="))
        assertEquals(false, automaton.putNewString("  "))
        assertEquals(false, automaton.putNewString("."))
        assertEquals(false, automaton.putNewString("\n"))
        assertEquals(false, automaton.putNewString("\\"))
        assertEquals(false, automaton.putNewString("a/"))
        assertEquals(false, automaton.putNewString("/1"))
        assertEquals(false, automaton.putNewString("1/"))
        assertEquals(false, automaton.putNewString("/a"))
        assertEquals(false, automaton.putNewString("/-"))
        assertEquals(false, automaton.putNewString("+/"))
    }
}