package lexicalAnalyzer

class LexerHelper(private val sourceCode: String) {
    private var _line = 1
    val line: Int
        get() = _line
    private var position = 0
    private var lookahead = 0

    fun getNextChar(): Char? {
        if (sourceCode.length > position) {
            val char = sourceCode[position]
            position += 1
            lookahead = position
            if (char.toInt() == 10) {
                _line += 1
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
        getNextChar() ?: return
        //_line += 1
    }

    fun returnChar() {
        if(position < sourceCode.length) {
            position -= 1
            val char = sourceCode[position]
            if(char.toInt() == 10) {
                _line -= 1
            }
        }
    }

    fun upNewLine() {
        _line += 1
    }
}