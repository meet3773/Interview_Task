package com.meet.socialmediataskk.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meet.socialmediataskk.model.Post
import com.meet.socialmediataskk.model.PostData
import com.meet.socialmediataskk.network.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    private val _posts = MutableLiveData<List<PostData>>()
    val posts: LiveData<List<PostData>> = _posts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getPosts(1).fold(
                    onSuccess = { posts ->
                        _posts.value = posts
                        _error.value = null
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Unknown error occurred"
                        Log.e("PostViewModel", "Error fetching posts", exception)
                    }
                )
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
                Log.e("PostViewModel", "Error in fetchPosts", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleLike(postId: String) {
        viewModelScope.launch {
            try {
                val currentPosts = _posts.value?.toMutableList() ?: return@launch
                val postIndex = currentPosts.indexOfFirst { it.post?._id == postId }
                if (postIndex == -1) return@launch

                currentPosts[postIndex].post?.let { post ->
                    val updatedPost = post.copy(
                        selfLike = !(post.selfLike ?: false),
                        TotalLike = if (post.selfLike == true) (post.TotalLike ?: 1) - 1 else (post.TotalLike ?: 0) + 1
                    )
                    currentPosts[postIndex] = PostData(updatedPost)
                    _posts.value = currentPosts
                }

                repository.toggleLike(postId).fold(
                    onSuccess = {  },
                    onFailure = { exception ->
                        _posts.value = _posts.value?.toMutableList()
                        _error.value = "Failed to update like status"
                        Log.e("PostViewModel", "Error toggling like", exception)
                    }
                )
            } catch (e: Exception) {
                _error.value = "Failed to update like status"
                Log.e("PostViewModel", "Error in toggleLike", e)
            }
        }
    }
}