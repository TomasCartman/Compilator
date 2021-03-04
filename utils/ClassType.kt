package utils

const val RELOP = "relationalOperator"
const val ID = "identifier"
const val NUMBER = "number"
const val KEYWORD = "keyword"
const val AROP = "arithmeticOperator"
const val LOGOP = "logicalOperator"
const val COMMENT = "comment"
const val STRING = "string"
const val DELIM = "delimiter"

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