package com.tongsr.common.coil.svga

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

/**
 * @author tongsr
 * @version 1.0
 * @date 2023/7/23
 * @email ujffdtfivkg@gmail.com
 * @description SVGAUtils
 */

const val movieBinary = "movie.binary"
const val movieSpec = "movie.spec"

fun File.makeSureExist() {
    val dir = this
    if (dir.exists()) {
        if (!dir.isDirectory) {
            dir.deleteRecursively()
            dir.mkdirs()
        }
    } else {
        dir.mkdirs()
    }
}

fun File.isSVGAUnZipFile(): Boolean {

    fun hasChild(vararg fileNames: String): Boolean {
        if (this.isDirectory) {
            val childFileNames = this.list()?.toSet() ?: emptySet()
            return fileNames.any { childFileNames.contains(it) }
        }
        return false
    }

    return hasChild(movieBinary, movieSpec)

}

// 是否是 zip 文件
fun isZipFile(bytes: ByteArray): Boolean {
    return bytes.size > 4 && bytes[0].toInt() == 80 && bytes[1].toInt() == 75 && bytes[2].toInt() == 3 && bytes[3].toInt() == 4
}


fun readAsBytes(inputStream: InputStream): ByteArray {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        val byteArray = ByteArray(2048)
        while (true) {
            val count = inputStream.read(byteArray, 0, 2048)
            if (count <= 0) {
                break
            } else {
                byteArrayOutputStream.write(byteArray, 0, count)
            }
        }
        return byteArrayOutputStream.toByteArray()
    }
}