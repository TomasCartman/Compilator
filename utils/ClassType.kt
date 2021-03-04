package utils

const val RELOP = "relop"
const val ID = "id"
const val NUMBER = "number"
const val KEYWORD = "keyword"
const val AROP = "arop"
const val LOGOP = "logop"
const val COMMENT = "comment"
const val STRING = "string"

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
    }
}