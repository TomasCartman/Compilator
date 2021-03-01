package utils

const val RELOP = "relop"
const val ID = "id"
const val BOOLOP = "boolop"

class ClassType private constructor(type: String) {
    var type: String = type

    companion object {

        fun createRelOpType(): ClassType {
            return ClassType(RELOP)
        }

        fun createIdType(): ClassType {
            return ClassType(ID)
        }

        fun createBoolOpType(): ClassType {
            return ClassType(BOOLOP)
        }
    }
}