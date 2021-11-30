package util

import java.math.BigInteger
import java.security.MessageDigest

fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun List<String>.toLongs() = map { it.toLong() }

fun List<String>.toInts() = map { it.toInt() }