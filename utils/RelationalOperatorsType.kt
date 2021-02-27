package utils

class RelationalOperatorsType {
    companion object {
        val EQ = "=="
        val NE = "!="
        val GT = ">"
        val LT = "<"
        val GE = ">="
        val LE = "<="
        val AS = "="

        fun getType(char1: Char, char2: Char?): String {
            var type = ""
            when(char1) {
                '=' -> {
                    type = if(char2 != null) EQ
                    else AS
                }
                '!' -> {
                    type = NE
                }
                '>' -> {
                    type = if(char2 != null) GE
                    else GT
                }
                '<' -> {
                    type = if(char2 != null) LE
                    else LT
                }
            }
            return type
        }
    }
}


