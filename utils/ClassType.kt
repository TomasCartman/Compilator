package utils

const val RELOP = "relop"
const val ID = "id"
const val NUMBER = "number"
const val KEYWORD = "keyword"

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
    }
}