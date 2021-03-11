package tests

import lexicalAnalyzer.AutomatonKeywords
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AutomatonKeywordsTest {
    private lateinit var automaton: AutomatonKeywords

    @BeforeEach
    fun setUp() {
        automaton = AutomatonKeywords()
    }

    @Test
    fun putNewString() {
        // Valid
        assertEquals(true, automaton.putNewString("var"))
        assertEquals(true, automaton.putNewString("const"))
        assertEquals(true, automaton.putNewString("typedef"))
        assertEquals(true, automaton.putNewString("struct"))
        assertEquals(true, automaton.putNewString("extends"))
        assertEquals(true, automaton.putNewString("procedure"))
        assertEquals(true, automaton.putNewString("function"))
        assertEquals(true, automaton.putNewString("start"))
        assertEquals(true, automaton.putNewString("return"))
        assertEquals(true, automaton.putNewString("if"))
        assertEquals(true, automaton.putNewString("else"))
        assertEquals(true, automaton.putNewString("then"))
        assertEquals(true, automaton.putNewString("while"))
        assertEquals(true, automaton.putNewString("read"))
        assertEquals(true, automaton.putNewString("print"))
        assertEquals(true, automaton.putNewString("int"))
        assertEquals(true, automaton.putNewString("real"))
        assertEquals(true, automaton.putNewString("boolean"))
        assertEquals(true, automaton.putNewString("string"))
        assertEquals(true, automaton.putNewString("true"))
        assertEquals(true, automaton.putNewString("false"))
        assertEquals(true, automaton.putNewString("global"))
        assertEquals(true, automaton.putNewString("local"))

        // Invalid
        assertEquals(false, automaton.putNewString("varvar"))
        assertEquals(false, automaton.putNewString("extend"))
        assertEquals(false, automaton.putNewString("cont"))
        assertEquals(false, automaton.putNewString("init"))
        assertEquals(false, automaton.putNewString("23213"))
        assertEquals(false, automaton.putNewString(" "))
        assertEquals(false, automaton.putNewString("fi"))
        assertEquals(false, automaton.putNewString("elses"))
        assertEquals(false, automaton.putNewString("\n"))
        assertEquals(false, automaton.putNewString("\\"))
        assertEquals(false, automaton.putNewString("books"))
        assertEquals(false, automaton.putNewString("locale"))
        assertEquals(false, automaton.putNewString("reel"))
        assertEquals(false, automaton.putNewString("232.2"))
        assertEquals(false, automaton.putNewString("falsent"))
    }
}