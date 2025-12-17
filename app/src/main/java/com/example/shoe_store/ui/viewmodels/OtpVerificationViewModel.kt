package com.example.shoe_store.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OtpVerificationViewModel : ViewModel() {
    // Данные OTP - mutableStateOf для простых переменных
    var otpCode by mutableStateOf("")
    var email by mutableStateOf("") // Email для верификации

    // Ошибки - mutableStateOf
    var otpError by mutableStateOf<String?>(null)
    var showOtpError by mutableStateOf(false)

    // Состояния таймера - StateFlow для реактивных потоков
    private var timerJob: Job? = null
    private val _timerSeconds = MutableStateFlow(60)
    val timerSeconds: StateFlow<Int> = _timerSeconds.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private val _hasStartedTyping = MutableStateFlow(false)
    val hasStartedTyping: StateFlow<Boolean> = _hasStartedTyping.asStateFlow()

    // Загрузка и состояние - StateFlow
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _otpState = MutableStateFlow<OtpState>(OtpState.Idle)
    val otpState: StateFlow<OtpState> = _otpState.asStateFlow()

    // Запуск таймера (01:00)
    fun startTimer() {
        if (_isTimerRunning.value) return

        _timerSeconds.value = 60
        _hasStartedTyping.value = true
        _isTimerRunning.value = true

        timerJob = viewModelScope.launch {
            while (_timerSeconds.value > 0 && _isTimerRunning.value) {
                delay(1000L)
                _timerSeconds.value--
            }
            if (_timerSeconds.value == 0) {
                _isTimerRunning.value = false
            }
        }
    }

    // Сброс таймера
    fun resetTimer() {
        timerJob?.cancel()
        _timerSeconds.value = 60
        _isTimerRunning.value = true
        startTimer()
    }

    // Форматирование времени
    fun formatTimer(): String {
        val minutes = _timerSeconds.value / 60
        val seconds = _timerSeconds.value % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    // Обновление OTP кода
    fun updateOtpCode(code: String) {
        otpCode = code
        showOtpError = false
        otpError = null

        if (!_hasStartedTyping.value && code.isNotEmpty()) {
            _hasStartedTyping.value = true
            startTimer()
        }

        // Автоматическая отправка при вводе 6 цифр
        if (code.length == 6) {
            verifyOtp()
        }
    }

    // Верификация OTP (симуляция)
    fun verifyOtp() {
        if (otpCode.length != 6) {
            otpError = "Введите 6-значный код"
            showOtpError = true
            _otpState.value = OtpState.Error("Введите 6-значный код")
            return
        }

        _isLoading.value = true
        _otpState.value = OtpState.Loading
        showOtpError = false

        viewModelScope.launch {
            try {
                delay(1500) // Имитация сетевого запроса

                // Проверяем код (в реальном приложении здесь API запрос)
                if (otpCode == "123456") { // Тестовый код для демонстрации
                    _otpState.value = OtpState.Success
                    println("✅ OTP верификация успешна для email: $email")
                } else {
                    otpError = "Неверный код. Попробуйте еще раз"
                    showOtpError = true
                    _otpState.value = OtpState.Error("Неверный код")
                }
            } catch (e: Exception) {
                otpError = "Ошибка сети: ${e.message}"
                showOtpError = true
                _otpState.value = OtpState.Error("Ошибка сети")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Повторная отправка OTP (симуляция)
    fun resendOtp() {
        if (!_isTimerRunning.value && _timerSeconds.value == 0) {
            resetTimer()
            println("📧 Запрошена повторная отправка OTP на: $email")
            // Здесь будет API запрос на повторную отправку
        }
    }

    // Установка email
    fun setEmailForVerification(email: String) {
        this.email = email
        println("📧 Email установлен для OTP верификации: $email")
    }

    // Сброс состояния
    fun resetState() {
        otpCode = ""
        otpError = null
        showOtpError = false
        _otpState.value = OtpState.Idle
        _isLoading.value = false
        timerJob?.cancel()
        _timerSeconds.value = 60
        _isTimerRunning.value = false
        _hasStartedTyping.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}

sealed class OtpState {
    object Idle : OtpState()
    object Loading : OtpState()
    object Success : OtpState()
    data class Error(val message: String) : OtpState()
}