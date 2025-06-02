package com.meet.socialmediataskk.model

data class Post(
    val _id: String?,
    val userId: User?,
    val description: String?,
    val media: List<Media> = emptyList(),
    val TotalLike: Int = 0,
    val selfLike: Boolean = false,
    val comments: Int = 0
)