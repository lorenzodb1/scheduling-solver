package io

import java.io.File
import java.io.InputStream

fun readFile(path: String) : String {
    val inputStream: InputStream = File(path).inputStream()

    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}

fun writeFile(path: String, output: String) : Boolean {
    File(path).printWriter().use { out ->
        out.print(output)
    }

    return true
}