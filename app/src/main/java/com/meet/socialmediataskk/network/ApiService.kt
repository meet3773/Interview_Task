package com.meet.socialmediataskk.network

import com.meet.socialmediataskk.model.PostRequest
import com.meet.socialmediataskk.model.PostResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


interface ApiService {
    @POST("api/v2/post/getPost")
    suspend fun getPosts(
        @Header("Authorization") token: String,
        @Body request: PostRequest
    ): Response<PostResponse>

    companion object {
        private const val BASE_URL = "http://43.205.16.96:3000/"

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}

