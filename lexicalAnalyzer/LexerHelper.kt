package lexicalAnalyzer

class LexerHelper(private val sourceCode: String) {
    private var line = 1
    private var position = 0
    private var lookahead = 0

    fun getNextChar(): Char? {
        if (sourceCode.length > position) {
            val char = sourceCode[position]
            position += 1
            lookahead = position
            if (char.toInt() == 10) {
                line += 1
            }
            return  char
        }
        return null
    }

    fun getNextLookaheadChar(): Char? {
        if (sourceCode.length > lookahead && lookahead >= 0) {
            val char = sourceCode[lookahead]
            lookahead += 1
            if (char.toInt() == 10) {
                lookahead = -1
            }
            return char
        }
        return null
    }

    fun jumpLine() {
        var char = getNextChar() ?: return
        while (char.toInt() != 10) {
            char = getNextChar() ?: return
        }
        line += 1
    }
}