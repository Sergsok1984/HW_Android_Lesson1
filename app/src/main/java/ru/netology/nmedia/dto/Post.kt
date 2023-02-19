package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    var likes: Long = 999,
    var views: Long = 999999,
    var share: Long = 1099,
    var video: String = "https://youtu.be/"
)
