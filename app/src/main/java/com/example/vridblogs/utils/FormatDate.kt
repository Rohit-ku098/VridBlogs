package com.example.vridblogs.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(input: String): String {

    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM dd yyyy", Locale.getDefault())

    val date = inputFormat.parse(input)
    val formattedDate = outputFormat.format(date)
    return formattedDate
}