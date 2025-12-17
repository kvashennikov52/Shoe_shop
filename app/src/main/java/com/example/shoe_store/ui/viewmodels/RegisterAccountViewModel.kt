package com.example.shoe_store.store.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class RegisterAccountViewModel : ViewModel() {

    var uiState by mutableStateOf(RegisterAccountUiState())
        private set

    private val emailPattern = Pattern.compile("^[a-z0-9]+@([a-z0-9]+\\.[a-z]{2,})$")

    fun updateName(newValue: String) {
        uiState = uiState.copy(name = newValue)
    }

    fun updateEmail(newValue: String) {
        val isValid = emailPattern.matcher(newValue).matches() || newValue.isEmpty()
        uiState = uiState.copy(
            email = newValue.lowercase(),
            emailError = !isValid && newValue.isNotEmpty()
        )
    }

    fun updatePassword(newValue: String) {
        uiState = uiState.copy(password = newValue)
    }

    fun togglePasswordVisibility() {
        uiState = uiState.copy(isPasswordVisible = !uiState.isPasswordVisible)
    }

    fun toggleTermsAccepted() {
        uiState = uiState.copy(isTermsAccepted = !uiState.isTermsAccepted)
    }

    fun register(
        onNavigateToSignIn: () -> Unit = {},
        onSignUpSuccess: () -> Unit = {}
    ) {
        if (uiState.emailError) {
            uiState = uiState.copy(dialogMessage = "Некорректный Email. Проверьте формат: имя@домен.xx")
            return
        }

        if (!uiState.isFormValid) {
            uiState = uiState.copy(dialogMessage = "Пожалуйста, заполните все поля и примите условия.")
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, dialogMessage = null)
            try {
                // Имитация запроса на сервер
                delay(1500)

                println("✅ Регистрация успешна!")
                println("📧 OTP отправлен на email: ${uiState.email}")

                // Вызываем коллбэк успешной регистрации
                onSignUpSuccess()

            } catch (e: Exception) {
                uiState = uiState.copy(dialogMessage = "Ошибка регистрации: ${e.message ?: "Нет соединения с Интернетом"}")
            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    fun dismissDialog() {
        uiState = uiState.copy(dialogMessage = null)
    }
}

data class RegisterAccountUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isTermsAccepted: Boolean = false,
    val isLoading: Boolean = false,
    val dialogMessage: String? = null,
    val emailError: Boolean = false
) {
    val isFormValid: Boolean
        get() = name.isNotBlank() &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                isTermsAccepted &&
                !emailError
}