package com.example.synhub.shared.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun createMultipartImage(
    context: Context,
    uri: Uri
): MultipartBody.Part {

    val inputStream = context.contentResolver.openInputStream(uri)
        ?: throw IllegalArgumentException("No se pudo abrir la imagen")

    val bytes = inputStream.readBytes()

    val requestBody = bytes.toRequestBody(
        "image/*".toMediaTypeOrNull()
    )

    return MultipartBody.Part.createFormData(
        "file",
        "group_image.jpg",
        requestBody
    )
}