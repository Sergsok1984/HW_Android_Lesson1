package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 999,
    var share: Int = 1099,
    var views: Int = 999999,
    var likedByMe: Boolean = false
)
