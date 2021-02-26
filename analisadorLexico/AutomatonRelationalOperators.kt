package analisadorLexico

class AutomatonRelationalOperators {
    private val lexemeList = mutableListOf<Char>()

    fun putNewChar(c: Char) {
        lexemeList.add(c)
    }

    fun testAutomaton(): Boolean {
        return false
    }
}