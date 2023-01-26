package ru.netology.nmedia.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.roundToInt

typealias OnLikeListener = (post: Post) -> Unit


class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnLikeListener,
    private val onViewListener: OnLikeListener

) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener, onViewListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnLikeListener,
    private val onViewListener: OnLikeListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likesCount.text = increment(post.likes)
            shareCount.text = increment(post.share)
            viewsCount.text = increment(post.views)

        likes.setImageResource(
            if (post.likedByMe) R.drawable.ic_liked_24dp else R.drawable.ic_likes_24dp
        )
        likes.setOnClickListener {
            onLikeListener(post)
        }
        share.setOnClickListener {
            onShareListener(post)
        }
        view.setOnClickListener {
            onViewListener(post)
        }
    }
}
    private fun increment(count: Long): String {
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

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}
