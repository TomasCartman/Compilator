package parser.exceptions

import utils.Token
import java.lang.Exception

class ParserException(line: Int, receivedToken: Token?, expectedTokens: List<String>) : Exception() {
    init {
        throw ParserException(line, receivedToken, expectedTokens)
    }
}