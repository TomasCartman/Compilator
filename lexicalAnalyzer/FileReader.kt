package lexicalAnalyzer

import java.io.File

class FileReader {

    companion object {
        fun readFileAsTextUsingInputStream(fileName: String): String =
            File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)

    }
}