package utils

// This file will be used in the next phase of the compiler
class SymbolTable {

    companion object {
        private var INSTANCE: SymbolTable? = null

        fun getInstance(): SymbolTable {
            if(INSTANCE == null) {
                INSTANCE = SymbolTable()
            }
            return INSTANCE as SymbolTable
        }
    }
}