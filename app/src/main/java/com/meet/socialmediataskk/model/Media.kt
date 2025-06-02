package com.meet.socialmediataskk.model

data class Media(
    val url: String?,
    val type: String?,
    val thumbnail: String?,
    val aspectRatio: Float = 1.0f
)