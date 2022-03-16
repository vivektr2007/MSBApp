package xyz.teknol.utils.extensions

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
var DATE_TIME_FORMAT = "MM/dd/yyyy hh:mm a"
fun toString(date: Date): String? {
    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
}

fun toDate(strDate: String): Date? {
    return try {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).parse(strDate)
    } catch (e: Exception) {
        null
    }
}
fun Date.toServerDate(): String? {
    return SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(this)
}