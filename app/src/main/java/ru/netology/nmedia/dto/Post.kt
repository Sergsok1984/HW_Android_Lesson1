package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Long = 999,
    val share: Long = 1099,
    val views: Long = 999999,
    val likedByMe: Boolean,
    var video: String? = null
)
