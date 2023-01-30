package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: PostViewModel by viewModels()

    private val interactionListener = object : OnInteractionListener {
        override fun onEdit(post: Post) {
            viewModel.edit(post)
            binding.editGroup.visibility = View.VISIBLE
            binding.editContentPreview.text = post.content
        }

        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onShare(post: Post) {
            viewModel.shareById(post.id)
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
        }

        override fun onView(post: Post) {
            viewModel.viewById(post.id)
        }
    }
    private val adapter = PostsAdapter(interactionListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        subscribe()
        setupListeners()
        cancel()
    }

    private fun initView() {
        binding.list.adapter = adapter
    }

    private fun subscribe() {
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
//        binding.cancelEdit.setOnClickListener {
//            viewModel.cancel()
//        }

        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                with(binding.editText) {
                    requestFocus()
                    setText(post.content)
                    AndroidUtils.showKeyboard(this)
                }
            } else {
                with(binding.editText) {
                    setText("")
                    clearFocus()
                    AndroidUtils.hideKeyboard(this)
                }
            }
        }
    }


    private fun setupListeners() {
        binding.save.setOnClickListener {
            with(binding.editText) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.changeContent(text.toString())
                    viewModel.save()
                    binding.editGroup.visibility = View.GONE
                    setText("")
                    clearFocus()
                    AndroidUtils.hideKeyboard(this)
                }
            }
        }
    }


    private fun cancel() {
        binding.cancelEdit.setOnClickListener {
            with(binding.editText) {
                viewModel.cancel()
                binding.editGroup.visibility = View.GONE
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
    }
}




