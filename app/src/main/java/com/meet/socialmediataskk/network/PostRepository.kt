package com.meet.socialmediataskk.network

import com.meet.socialmediataskk.model.PostData
import com.meet.socialmediataskk.model.PostRequest


class PostRepository(private val apiService: ApiService) {

    companion object {
        private const val TOKEN =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJfaWQiOiI2MTEiLCJuYW1lIjoiTWFuaWoiLCJjb3VudHJ5Q29kZSI6Iis5MSIsImVtYWlsIjoiIiwicGhvbmUiOjk4NzkxNjk3ODgsImRlc2NyaXB0aW9uIjoiSGV5LCBJJ20gaW4gVGVwbm90IiwidGFncyI6IiIsInByb2ZpbGUiOiJ1cGxvYWRzL3Byb2ZpbGUvODM5NUZCMjQtQ0NDQS00MzRFLUI5NTYtNDY1ODA3QTUyNkUzLTI2MzgtMDAwMDA1NkYyNjMyOEM3Ri5qcGVnIiwib3RwU2VuZENvdW50IjoxLCJsYXN0T3RwU2VuZERhdGUiOiIyMDI1LTAxLTA3VDA4OjMyOjAyLjE0NloiLCJpc1VzZXJWZXJpZmllZCI6dHJ1ZSwiaXNCYW5uZWQiOmZhbHNlLCJkYXRlT2ZCaXJ0aCI6IjE5ODAtMDEtMDFUMDA6MDA6MDAuMDAwWiIsImxpbmsiOiIiLCJpc0RlbGV0ZWQiOmZhbHNlLCJvbGRQaG9uZSI6bnVsbCwidXNlclR5cGUiOiJVc2VyIiwiY3JlYXRlQXQiOiIyMDI0LTEwLTA4VDA1OjA2OjQwLjMwMFoiLCJ1cGRhdGVBdCI6IjIwMjUtMDEtMDdUMDg6MzI6MzkuMzQzWiIsImJpb1VwZGF0ZSI6IjIwMjUtMDEtMDdUMDg6MzI6MzkuMzQzWiIsIl9fdiI6MCwiaXNPdHBWZXJpZmllZCI6dHJ1ZX0sImlhdCI6MTc0ODQxNDU2Mn0.pZ7p06NGGbQOfN8mHVqX0OdzgFejlRjooTwLz2UUqLg"
    }

    suspend fun getPosts(page: Int): Result<List<PostData>> {
        return try {
            val response = apiService.getPosts(TOKEN, PostRequest())
            if (response.isSuccessful && response.body() != null) {
                val posts = response.body()?.data ?: emptyList()
                Result.success(posts)
            } else {
                Result.failure(Exception("Failed to fetch posts: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun toggleLike(postId: String): Result<Boolean> {
        return try {
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}