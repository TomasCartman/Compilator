package utils

const val RELOP = "REL"
const val ID = "IDE"
const val NUMBER = "NRO"
const val KEYWORD = "PRE"
const val AROP = "ART"
const val LOGOP = "LOG"
const val COMMENT = "comment"
const val STRING = "CAD"
const val DELIM = "DEL"

class ClassType private constructor(type: String) {
    var type: String = type

    companion object {

        fun createRelOpType(): ClassType {
            return ClassType(RELOP)
        }

        fun createIdType(): ClassType {
            return ClassType(ID)
        }

        fun createNumberType(): ClassType {
            return ClassType(NUMBER)
        }

        fun createKeywordType(): ClassType {
            return ClassType(KEYWORD)
        }

        fun createArOpType(): ClassType {
            return ClassType(AROP)
        }

        fun createLogOpType(): ClassType {
            return ClassType(LOGOP)
        }

        fun createCommentType(): ClassType {
            return ClassType(COMMENT)
        }

        fun createStringType(): ClassType {
            return ClassType(STRING)
        }

        fun createDelimiterType(): ClassType {
            return ClassType(DELIM)
        }
    }
}