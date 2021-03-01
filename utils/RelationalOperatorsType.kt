package utils

class RelationalOperatorsType {
    companion object {
        private const val EQ = "=="
        private const val NE = "!="
        private const val GT = ">"
        private const val LT = "<"
        private const val GE = ">="
        private const val LE = "<="
        private const val AS = "="

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


