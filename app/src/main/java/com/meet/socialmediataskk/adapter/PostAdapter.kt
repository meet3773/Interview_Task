package com.meet.socialmediataskk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meet.socialmediataskk.R
import com.meet.socialmediataskk.model.Post
import com.meet.socialmediataskk.model.PostData

class PostAdapter(
    private val onLikeClick: (String) -> Unit
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var posts: List<PostData> = emptyList()

    fun updatePosts(newPosts: List<PostData>) {
        posts = newPosts.filterNot { it.post == null }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        posts.getOrNull(position)?.post?.let { post ->
            holder.bind(post)
        }
    }

    override fun getItemCount(): Int = posts.size

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.postImage)
        private val captionText: TextView = itemView.findViewById(R.id.postCaption)
        private val likeButton: ImageView = itemView.findViewById(R.id.likeButton)
        private val likeCount: TextView = itemView.findViewById(R.id.likeCount)
        private val userName: TextView = itemView.findViewById(R.id.userName)

        fun bind(post: Post) {
            try {
                post.media.firstOrNull()?.let { media ->
                    if (!media.url.isNullOrEmpty()) {
                        Glide.with(itemView.context)
                            .load("https://d3b13iucq1ptzy.cloudfront.net/uploads/posts/image/${media.url}")
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.nodata)
                            .into(imageView)
                    }
                }

                captionText.text = if (post.description.isNullOrBlank()) {
                    itemView.context.getString(R.string.no_caption_available)
                } else {
                    post.description
                }

                likeCount.text = "${post.TotalLike ?: 0} likes"
                userName.text = post.userId?.name.orEmpty()

                likeButton.setImageResource(
                    if (post.selfLike == true) R.drawable.like
                    else R.drawable.unlike
                )

                likeButton.setOnClickListener {
                    post._id?.let { id -> onLikeClick(id) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                captionText.text = itemView.context.getString(R.string.no_caption_available)
                likeCount.text = "0 likes"
                userName.text = ""
            }
        }
    }
}
