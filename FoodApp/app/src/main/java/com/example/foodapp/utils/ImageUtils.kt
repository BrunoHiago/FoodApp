package com.example.foodapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.util.Base64

object ImageUtils {
    fun base64ToBitmap(base64Str: String): Bitmap {
        if (base64Str.isEmpty()) {
            throw IllegalArgumentException("A string base64 está vazia.")
        }
        val decodedBytes = Base64.getDecoder().decode(base64Str)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()

        return Base64.getEncoder().encodeToString(byteArray)
    }
}