package com.example.shoe_store.ui.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoe_store.data.model.UserProfile
import com.example.shoe_store.data.network.RetrofitClient
import kotlinx.coroutines.launch

sealed class ProfileUiState {
    object Idle : ProfileUiState()
    object Loading : ProfileUiState()
    object Success : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}

class ProfileViewModel : ViewModel() {
    private val TAG = "ProfileViewModel"

    // API ключ для домена fwjozcsirpzcptegqkbo
    private val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZ3am96Y3NpcnB6Y3B0ZWdxa2JvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjYwNzkyMTAsImV4cCI6MjA4MTY1NTIxMH0.Bb2GIa8OnZFIL6J8TJdWSgq0BKsM4lx0Ar-C-WquTOA"
    private val AUTH_TOKEN = "Bearer $API_KEY"

    // UI состояния
    var uiState by mutableStateOf<ProfileUiState>(ProfileUiState.Idle)
        private set

    var isEditing by mutableStateOf(false)

    // Данные профиля
    var name by mutableStateOf("")
    var lastName by mutableStateOf("")
    var address by mutableStateOf("")
    var phone by mutableStateOf("")
    var bitmapPhoto by mutableStateOf<Bitmap?>(null)

    // Текущий ID пользователя (должен получаться из авторизации)
    private var currentUserId: String? = null

    // Временное решение - устанавливаем тестовый ID
    init {
        // TODO: Замените на реальное получение ID из Auth
        currentUserId = getCurrentUserId()
        Log.d(TAG, "Инициализирован user_id: $currentUserId")
    }

    // ========== ЗАГРУЗКА ПРОФИЛЯ ==========
    fun loadProfile() {
        viewModelScope.launch {
            uiState = ProfileUiState.Loading
            try {
                val userId = currentUserId
                if (userId == null) {
                    Log.e(TAG, "User ID is null")
                    uiState = ProfileUiState.Error("Пользователь не авторизован")
                    return@launch
                }

                Log.d(TAG, "Загрузка профиля для user_id: $userId")

                // Вызов API с правильными параметрами
                val response = RetrofitClient.apiService.getUserProfile(
                    apiKey = API_KEY,
                    token = AUTH_TOKEN,
                    userIdFilter = "eq.$userId"
                )

                if (response.isSuccessful) {
                    val profileList = response.body()

                    if (profileList != null && profileList.isNotEmpty()) {
                        val profile = profileList[0]
                        name = profile.firstName ?: ""
                        lastName = profile.lastName ?: ""
                        address = profile.address ?: ""
                        phone = profile.phone ?: ""

                        Log.d(TAG, "Профиль загружен: $name $lastName")
                        uiState = ProfileUiState.Success
                    } else {
                        // Профиль не найден - это нормально для нового пользователя
                        Log.d(TAG, "Профиль не найден, устанавливаем пустые значения")
                        name = ""
                        lastName = ""
                        address = ""
                        phone = ""
                        uiState = ProfileUiState.Success
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "Ошибка загрузки: ${response.code()} - $errorBody")
                    uiState = ProfileUiState.Error("Ошибка загрузки: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Исключение при загрузке", e)
                uiState = ProfileUiState.Error("Ошибка сети: ${e.message ?: "Неизвестная ошибка"}")
            }
        }
    }

    // ========== СОХРАНЕНИЕ ПРОФИЛЯ ==========
    fun saveProfile() {
        viewModelScope.launch {
            uiState = ProfileUiState.Loading
            try {
                val userId = currentUserId
                if (userId == null) {
                    uiState = ProfileUiState.Error("Пользователь не авторизован")
                    return@launch
                }

                Log.d(TAG, "Сохранение профиля для user_id: $userId")

                // Создаем объект для обновления
                val profile = UserProfile(
                    firstName = name,
                    lastName = lastName,
                    address = address,
                    phone = phone
                )

                // Правильный вызов API - все параметры по именам
                val response = RetrofitClient.apiService.updateProfile(
                    apiKey = API_KEY,
                    token = AUTH_TOKEN,
                    userIdFilter = "eq.$userId",
                    prefer = "return=representation",
                    profile = profile
                )

                if (response.isSuccessful) {
                    val updatedList = response.body()
                    if (updatedList != null && updatedList.isNotEmpty()) {
                        val updatedProfile = updatedList[0]
                        name = updatedProfile.firstName ?: ""
                        lastName = updatedProfile.lastName ?: ""
                        address = updatedProfile.address ?: ""
                        phone = updatedProfile.phone ?: ""
                    }

                    isEditing = false
                    Log.d(TAG, "Профиль успешно сохранен")
                    uiState = ProfileUiState.Success
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(TAG, "Ошибка сохранения: ${response.code()} - $errorBody")
                    uiState = ProfileUiState.Error("Ошибка сохранения: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Исключение при сохранении", e)
                uiState = ProfileUiState.Error("Ошибка сети: ${e.message ?: "Неизвестная ошибка"}")
            }
        }
    }

    // ========== ПОЛУЧЕНИЕ USER_ID (ВРЕМЕННОЕ РЕШЕНИЕ) ==========
    private fun getCurrentUserId(): String? {
        // TODO: Реализуйте получение ID из вашей системы авторизации

        // Вариант 1: Если используете Firebase
        // return Firebase.auth.currentUser?.uid

        // Вариант 2: Если используете Supabase Auth
        // return supabase.auth.currentUser?.id

        // Вариант 3: Хранить в SharedPreferences после входа
        // val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
        // return prefs.getString("user_id", null)

        // ВРЕМЕННО: используем тестовый ID (замените на реальный)
        // Пока что используем тот ID, который создали в Supabase вручную
        return "89af550a-ddc0-4314-9f78-b6aef9f65778"
    }

    // ========== РАБОТА С ФОТО ==========
    fun onPhotoCaptured(bitmap: Bitmap) {
        bitmapPhoto = bitmap
        Log.d(TAG, "Фото захвачено")
    }

    // ========== ЗАКРЫТИЕ ОШИБКИ ==========
    fun dismissError() {
        uiState = ProfileUiState.Idle
    }

    // ========== УСТАНОВКА USER_ID ВРУЧНУЮ (ДЛЯ ТЕСТА) ==========
    fun setUserIdForTesting(userId: String) {
        currentUserId = userId
        Log.d(TAG, "Установлен тестовый user_id: $userId")
    }
}