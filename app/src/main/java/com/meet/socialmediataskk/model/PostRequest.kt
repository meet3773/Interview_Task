package com.meet.socialmediataskk.model

data class PostRequest(
    val postIdList: List<String> = emptyList(),
    val shots: Boolean = false
)
