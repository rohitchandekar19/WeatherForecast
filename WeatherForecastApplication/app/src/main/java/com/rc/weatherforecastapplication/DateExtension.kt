package com.rc.weatherforecastapplication

import java.text.SimpleDateFormat

private const val PATTERN = "yyyy-MM-dd HH:mm:ss"

fun Long.getDisplayDateTime(): String {
    val simpleDateFormat = SimpleDateFormat(PATTERN)
    return simpleDateFormat.format(this * 1000)
}