package utils

data class Token (
    var type: ClassType,
    var value: String = "",
    var line: Int = 0,
    var expected: List<String> = listOf()
)