package parser.exceptions

import utils.Token
import java.lang.Exception

class ParserException(val line: Int, val receivedToken: Token?, val expectedTokens: List<String>) : Exception()