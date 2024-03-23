package com.litecodez.lawresources
import org.apache.commons.compress.compressors.CompressorStreamFactory
import java.io.ByteArrayOutputStream
import java.util.Base64

/**
 * The ApiEncode class provides functionality for encoding and decoding strings
 * using a custom encoding scheme. The class also provides a utility method for
 * decoding Base64 encoded strings.
 */
class ApiEncode {

    /**
     * Encodes the input string using a custom encoding scheme after converting it to Base64.
     *
     * @param inputStr The string to be encoded.
     * @return The encoded string using the custom encoding scheme.
     */
    fun encodeApi(inputStr: String): String {
        val charMap = mapOf(
            'a' to "&~-", 'b' to "&--~", 'c' to "&~_~", 'd' to "&~-__", 'e' to "&-~_",
            'f' to "&-~", 'g' to "&-_~", 'h' to "&~_-", 'i' to "&--", 'j' to "&_~~",
            'k' to "&_--", 'l' to "&~-~", 'm' to "&-_-", 'n' to "&~_-_", 'o' to "&~__~",
            'p' to "&-~-", 'q' to "&~-_-", 'r' to "&-_", 's' to "&~~-", 't' to "&-~~",
            'u' to "&~_--", 'v' to "&-_~_", 'w' to "&~", 'x' to "&_-~", 'y' to "&_--~",
            'z' to "&~--", '=' to "&__~~", ' ' to "&__", 'A' to "&~-.", 'B' to "&--~.",
            'C' to "&~_~.", 'D' to "&~-__.", 'E' to "&-~_.",
            'F' to "&-~.", 'G' to "&-_~.", 'H' to "&~_-.", 'I' to "&--.", 'J' to "&_~~.",
            'K' to "&_--.", 'L' to "&~-~.", 'M' to "&-_-.", 'N' to "&~_-_.", 'O' to "&~__~.",
            'P' to "&-~-.", 'Q' to "&~-_-.", 'R' to "&-_.", 'S' to "&~~-.", 'T' to "&-~~.",
            'U' to "&~_--.", 'V' to "&-_~_.", 'W' to "&~.", 'X' to "&_-~.", 'Y' to "&_--~.",
            'Z' to "&~--.", '0' to "&@", '1' to "&@@", '2' to "&@@@", '3' to "&@@@@",
            '4' to "&@@@@@", '5' to "&@@@@@@",
            '6' to "&@@@@@@@",
            '7' to "&@@@@@@@@", '8' to "&@@@@@@@@@", '9' to "&@@@@@@@@@@"
        )
        val byteArray = inputStr.toByteArray(Charsets.UTF_8)
        val base = Base64.getEncoder().encodeToString(byteArray)
        return base.map { charMap[it] ?: it }.joinToString(separator = "")
    }

    /**
     * Decodes the input string encoded with the custom encoding scheme back to its Base64 form.
     *
     * @param inputStr The custom-encoded string to be decoded.
     * @return The decoded string in its Base64 form.
     */
    fun decodeApi(inputStr: String): String {
        val charMap = mapOf(
            "&~-" to 'a', "&--~" to 'b', "&~_~" to 'c', "&~-__" to 'd', "&-~_" to 'e',
            "&-~" to 'f', "&-_~" to 'g', "&~_-" to 'h', "&--" to 'i', "&_~~" to 'j',
            "&_--" to 'k', "&~-~" to 'l', "&-_-" to 'm', "&~_-_" to 'n', "&~__~" to 'o',
            "&-~-" to 'p', "&~-_-" to 'q', "&-_" to 'r', "&~~-" to 's', "&-~~" to 't',
            "&~_--" to 'u', "&-_~_" to 'v', "&~" to 'w', "&_-~" to 'x', "&_--~" to 'y',
            "&~--" to 'z', "&__~~" to '=', "&__" to ' ', "&~-." to 'A', "&--~." to 'B',
            "&~_~." to 'C', "&~-__." to 'D', "&-~_." to 'E',
            "&-~." to 'F', "&-_~." to 'G', "&~_-." to 'H', "&--." to 'I', "&_~~." to 'J',
            "&_--." to 'K', "&~-~." to 'L', "&-_-." to 'M', "&~_-_." to 'N', "&~__~." to 'O',
            "&-~-." to 'P', "&~-_-." to 'Q', "&-_." to 'R', "&~~-." to 'S', "&-~~." to 'T',
            "&~_--." to 'U', "&-_~_." to 'V', "&~." to 'W', "&_-~." to 'X', "&_--~." to 'Y',
            "&~--." to 'Z', "&@" to '0', "&@@" to '1', "&@@@" to '2', "&@@@@" to '3', "&@@@@@" to '4',
            "&@@@@@@" to '5', "&@@@@@@@" to '6', "&@@@@@@@@" to '7', "&@@@@@@@@@" to '8', "&@@@@@@@@@@" to '9'
        )

        val outputStr = StringBuilder()
        val inputStrArr = inputStr.split("&")
        for (i in inputStrArr) {
            val key = "&$i"
            if (key == "&") {
                continue
            }
            outputStr.append(charMap[key])
        }
        return outputStr.toString()

    }
    /**
     * Decodes the Base64 encoded string back to its original form.
     *
     * @param str The Base64 encoded string to be decoded.
     * @return The decoded string in its original form.
     */
    fun deBase(str: String): String {
        val decodedBytes = Base64.getDecoder().decode(str)
        return String(decodedBytes, Charsets.UTF_8)
    }

}

fun String.toDecompressedString(): String {
    val compressedBytes = Base64.getDecoder().decode(this)
    val inputStream = compressedBytes.inputStream()
    val decompressorInputStream = CompressorStreamFactory().createCompressorInputStream(
        CompressorStreamFactory.GZIP, inputStream
    )

    val outputBytes = decompressorInputStream.readBytes()
    decompressorInputStream.close()
    return outputBytes.toString(Charsets.UTF_8)
}
fun String.toCompressedString(): String {
    val inputBytes = this.toByteArray()
    val outputStream = ByteArrayOutputStream()
    val compressorOutputStream = CompressorStreamFactory().createCompressorOutputStream(
        CompressorStreamFactory.GZIP, outputStream
    )
    compressorOutputStream.write(inputBytes)
    compressorOutputStream.close()
    outputStream.close()

    val compressedBytes = outputStream.toByteArray()
    return Base64.getEncoder().encodeToString(compressedBytes)
}