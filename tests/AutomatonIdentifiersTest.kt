package tests

import lexicalAnalyzer.AutomatonIdentifiers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AutomatonIdentifiersTest {
    private lateinit var automaton: AutomatonIdentifiers

    @BeforeEach
    fun setUp() {
        automaton = AutomatonIdentifiers()
    }

    @Test
    fun putNewString() {
        // Valid
        assertEquals(true, automaton.putNewString("i"))
        assertEquals(true, automaton.putNewString("b"))
        assertEquals(true, automaton.putNewString("ball"))
        assertEquals(true, automaton.putNewString("house"))
        assertEquals(true, automaton.putNewString("PeterNumber1"))
        assertEquals(true, automaton.putNewString("p1_2_3_4_5"))
        assertEquals(true, automaton.putNewString("Pp2_DfCDdE2__"))
        assertEquals(true, automaton.putNewString("p________"))
        assertEquals(true, automaton.putNewString("p_2_3_D_f_e_p"))
        assertEquals(true, automaton.putNewString("house2"))
        assertEquals(true, automaton.putNewString("T_E_S_T"))
        assertEquals(true, automaton.putNewString("R_E_S_T"))

        // Invalid
        assertEquals(false, automaton.putNewString("_test"))
        assertEquals(false, automaton.putNewString("_variable"))
        assertEquals(false, automaton.putNewString("1test"))
        assertEquals(false, automaton.putNewString("2321t"))
        assertEquals(false, automaton.putNewString("22_test_2"))
        assertEquals(false, automaton.putNewString(" "))
        assertEquals(false, automaton.putNewString("2_"))
        assertEquals(false, automaton.putNewString("5f"))
        assertEquals(false, automaton.putNewString("\n"))
        assertEquals(false, automaton.putNewString("\\"))
        assertEquals(false, automaton.putNewString("#232_2"))
        assertEquals(false, automaton.putNewString("&&fair"))
        assertEquals(false, automaton.putNewString("*ded1"))
    }

    @Test
    fun generateToken() {
    }
}