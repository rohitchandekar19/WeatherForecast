package com.rc.weatherforecastapplication

import java.text.SimpleDateFormat

private const val PATTERN = "EEEE dd MMM, yyyy HH:mm"

fun Long.getDisplayDateTime(): String {
    val simpleDateFormat = SimpleDateFormat(PATTERN)
    return simpleDateFormat.format(this * 1000)
}