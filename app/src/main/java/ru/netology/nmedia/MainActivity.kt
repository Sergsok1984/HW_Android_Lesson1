package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import ru.netology.nmedia.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "14 января в 10:15",
            likedByMe = false
        )

        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                likes?.setImageResource(R.drawable.ic_likes_24dp)
            }
            likesCount?.text = increment(post.likes)
            shareCount?.text = increment(post.share)
            viewsCount?.text = increment(post.views)

            likes?.setOnClickListener {
                Log.d("stuff", "like")
                post.likedByMe = !post.likedByMe
                likes.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24dp else R.drawable.ic_likes_24dp
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likesCount?.text = increment(post.likes)
            }
            share?.setOnClickListener {
                Log.d("stuff", "share")
                post.share++
                shareCount?.text = increment(post.share)
            }
            views?.setOnClickListener {
                Log.d("stuff", "view")
                post.views++
                viewsCount?.text = increment(post.views)
            }
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

