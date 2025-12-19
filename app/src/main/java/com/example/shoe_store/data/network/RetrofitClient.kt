package com.example.shoe_store.data.network

import com.example.shoe_store.data.model.UserProfile
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    // GET запрос - фильтр user_id передается как параметр
    @GET("rest/v1/profiles")
    suspend fun getUserProfile(
        @Header("apikey") apiKey: String,
        @Header("Authorization") token: String,
        @Query("user_id") userIdFilter: String
    ): Response<List<UserProfile>>

    // PATCH запрос - фильтр user_id в query параметрах
    @PATCH("rest/v1/profiles")
    suspend fun updateProfile(
        @Header("apikey") apiKey: String,
        @Header("Authorization") token: String,
        @Query("user_id") userIdFilter: String,
        @Header("Prefer") prefer: String = "return=representation",
        @Body profile: UserProfile
    ): Response<List<UserProfile>>
}

object RetrofitClient {
    private const val BASE_URL = "https://fwjozcsirpzcptegqkbo.supabase.co"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}