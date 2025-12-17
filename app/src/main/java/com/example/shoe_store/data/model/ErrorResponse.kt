// data/model/ErrorResponse.kt
package com.example.shoe_store.data.model

data class ErrorResponse(
    val error: String,
    val error_description: String? = null
)