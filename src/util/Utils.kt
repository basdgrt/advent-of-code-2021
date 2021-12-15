package util

import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun <T> priorityQueueBy(valueSelector: (T) -> Comparable<*>): PriorityQueue<T> {
    return PriorityQueue { a, b -> (valueSelector(a) as Comparable<Any>).compareTo(valueSelector(b)) }
}