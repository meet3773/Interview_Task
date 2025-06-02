package com.meet.socialmediataskk.model

data class PostResponse(
    val status: String?,
    val data: List<PostData>?,
    val totalCount: Int?,
    val currentPage: Int?,
    val message: String?
)
