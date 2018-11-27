package ru.mail.techotrack.lection6

import android.content.Context

fun convertPixelsToDp(px: Int, context: Context): Int {
    return convertPixelsToDp(px.toFloat(), context).toInt()
}

fun convertDpToPixel(dp: Int, context: Context): Int {
    return convertDpToPixel(dp.toFloat(), context).toInt()
}

fun convertPixelsToDp(px: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return px / metrics.density
}

fun convertDpToPixel(dp: Float, context: Context): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return dp * metrics.density
}
