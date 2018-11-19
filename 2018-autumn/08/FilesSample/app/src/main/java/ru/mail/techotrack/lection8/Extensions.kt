package ru.mail.techotrack.lection8

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.*
import java.net.URL

fun InputStream.asString() : String {
    return bufferedReader(Charsets.UTF_8).use { it.readText() }
}

fun InputStream.asBytes() : ByteArray {
    return buffered().readBytes()
}

fun File.asBitmap(width : Int, height : Int): Bitmap? {
    try {
        val inputStream = FileInputStream(this)
        val opt = BitmapFactory.Options()
        opt.inJustDecodeBounds = true
        BitmapFactory.decodeStream(inputStream, null, opt)
        val sc = calculateInSampleSize(opt, width, height)

        opt.inSampleSize = sc
        opt.inJustDecodeBounds = false
        val bitmap = BitmapFactory.decodeStream(FileInputStream(this), null, opt)
        bitmap?.run {
            Log.d("LOAD_IMAGE", " name = " + nameWithoutExtension + " w = " + bitmap.width + " h = " + bitmap.height)
        }
        return bitmap
    } catch (e: IOException) {
        Log.e("LoadImageTask", "LoadImageTask.LoadBitmap IOException " + e.message, e);
    }
    return null
}


fun URL.asFile(file : File) : File {
    val inputStream = openConnection().getInputStream()
    val outputStream = FileOutputStream(file)
    inputStream.copyTo(outputStream)
    inputStream.close()
    outputStream.close()
    return file
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight = height / 2
        val halfWidth = width / 2

        while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}
