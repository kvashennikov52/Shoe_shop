package com.example.shoe_store.data.model

data class VerifyOtpRequest(
    val email: String,
    val token: String,
    val type: String = "email"
)