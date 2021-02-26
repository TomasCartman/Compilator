package analisadorLexico

import com.sun.istack.internal.Nullable

class AutomatonRelationalOperators {
    private var lexemeList = mutableListOf<Char>()
    private var charPosition: Int = 0
    private var state: Int = 0

    fun putNewChar(c: Char): Boolean {
        lexemeList.add(c)

        return testLexeme()
    }

    fun resetAutomaton() {
        lexemeList = mutableListOf()
        charPosition = 0
        state = 0
    }

    private fun testLexeme(): Boolean {
        val char = nextChar() ?: return false
        var verificationResult = false
        when(state) {
            0 -> {
                if(char == '=') {
                    state = 1
                    verificationResult = true
                }
                if(char == '!') {
                    state = 2
                    verificationResult = false
                }
                if(char == '>') {
                    state = 3
                    verificationResult = true
                }
                if(char == '<') {
                    state = 4
                    verificationResult = true
                }
            }
            1, 2, 3, 4 -> {
                if(char == '=') {
                    state = 5
                    verificationResult = true
                }
            }
            else -> verificationResult = false
        }
        return verificationResult
    }

    @Nullable
    private fun nextChar(): Char? {
        if(lexemeList.size > charPosition) {
            val char = lexemeList[charPosition]
            charPosition += 1
            return char
        }
        return null
    }
}