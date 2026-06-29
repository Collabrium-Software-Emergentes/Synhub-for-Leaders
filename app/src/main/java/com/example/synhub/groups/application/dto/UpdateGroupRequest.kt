package com.example.synhub.groups.application.dto

import android.net.Uri

data class UpdateGroupRequest(
    val name: String?,
    val description: String?,
    val imageUri: Uri? = null
)