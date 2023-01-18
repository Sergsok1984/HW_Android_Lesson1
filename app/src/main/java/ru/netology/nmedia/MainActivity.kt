package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            binding.apply {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likes.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24dp else R.drawable.ic_likes_24dp
                )
                likesCount.text = increment(post.likes)
                shareCount.text = increment(post.share)
                viewsCount.text = increment(post.views)
            }
        }
        binding.likes.setOnClickListener {
            viewModel.like()
        }
        binding.share.setOnClickListener {
            viewModel.share()
        }
        binding.view.setOnClickListener {
            viewModel.view()
        }
    }

    private fun increment(count: Int): String {
        return if (count in 1000..1099) {
            val text = (count / 1000)
            text.toString() + "K"
        } else if (count in 1100..9_999) {
            var text = (count.toDouble() / 1000)
            text = (text * 10).roundToInt() / 10.0
            text.toString() + "K"
        } else if (count in 10_000..999_999) {
            val text = (count / 1000)
            text.toString() + "K"
        } else if (count >= 1_000_000) {
            val text = (count / 1_000_000)
            text.toString() + "M"
        } else count.toString()
    }
}

