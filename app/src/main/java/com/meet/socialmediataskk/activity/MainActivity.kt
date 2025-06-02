package com.meet.socialmediataskk.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meet.socialmediataskk.R
import com.meet.socialmediataskk.adapter.PostAdapter
import com.meet.socialmediataskk.network.ApiService
import com.meet.socialmediataskk.network.PostRepository
import com.meet.socialmediataskk.viewmodel.PostViewModel
import com.meet.socialmediataskk.viewmodel.PostViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PostViewModel
    private lateinit var adapter: PostAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

        adapter = PostAdapter { postId ->
            viewModel.toggleLike(postId)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        val repository = PostRepository(ApiService.create())
        val viewModelFactory = PostViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[PostViewModel::class.java]

        viewModel.posts.observe(this) { posts ->
            adapter.updatePosts(posts)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}