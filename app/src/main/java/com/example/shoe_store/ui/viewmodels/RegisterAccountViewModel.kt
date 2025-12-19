package com.example.shoe_store.store.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterAccountViewModel : ViewModel() {

    var uiState by mutableStateOf(RegisterAccountUiState())
        private set

    // ИСПРАВЛЕННОЕ регулярное выражение для email
    private val emailPattern = Regex("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.)+[A-Za-z]{2,}\$")

    fun updateName(newValue: String) {
        uiState = uiState.copy(name = newValue)
    }

    fun updateEmail(newValue: String) {
        // Используем исправленную логику проверки
        val isValid = isValidEmail(newValue) || newValue.isEmpty()
        uiState = uiState.copy(
            email = newValue, // убрал .lowercase() чтобы пользователь видел ввод как есть
            emailError = !isValid && newValue.isNotEmpty()
        )
    }

    // Новая функция для более корректной проверки email
    private fun isValidEmail(email: String): Boolean {
        if (email.isEmpty()) return false

        // 1. Проверяем что email не начинается и не заканчивается точкой
        if (email.startsWith(".") || email.endsWith(".")) return false

        // 2. Проверяем что есть один символ @
        val atCount = email.count { it == '@' }
        if (atCount != 1) return false

        // 3. Разделяем email на локальную часть и домен
        val parts = email.split("@")
        if (parts.size != 2) return false

        val localPart = parts[0]
        val domain = parts[1]

        // 4. Проверяем локальную часть
        if (localPart.isEmpty()) return false
        if (localPart.startsWith(".") || localPart.endsWith(".")) return false
        if (localPart.contains("..")) return false

        // 5. Проверяем домен
        if (domain.isEmpty()) return false
        if (domain.startsWith(".") || domain.endsWith(".")) return false
        if (domain.contains("..")) return false

        // 6. Проверяем что в домене есть хотя бы одна точка
        if (!domain.contains(".")) return false

        // 7. Проверяем доменную зону (после последней точки)
        val lastDotIndex = domain.lastIndexOf(".")
        val domainZone = domain.substring(lastDotIndex + 1)
        if (domainZone.length < 2) return false

        // 8. Проверяем специальные символы с помощью regex (опционально)
        return emailPattern.matches(email)
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
        onSignUpSuccess: (String) -> Unit = {} // Добавлен параметр для передачи email
    ) {
        if (uiState.emailError) {
            uiState = uiState.copy(dialogMessage = "Некорректный Email. Пример: name@domain.com")
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

                // Вызываем коллбэк успешной регистрации с передачей email
                onSignUpSuccess(uiState.email)

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