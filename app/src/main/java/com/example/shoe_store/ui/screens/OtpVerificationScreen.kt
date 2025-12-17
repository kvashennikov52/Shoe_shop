package com.example.shoe_store.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoe_store.ui.viewmodel.OtpVerificationViewModel

// Цвета
fun getAccent() = Color(0xFF48B2E7)
fun getBackground() = Color(0xFFFFFFFF)
fun getBlock() = Color(0xFFF5F5F5)
fun getText() = Color(0xFF000000)
fun getSubTextDark() = Color(0xFF666666)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    email: String = "",
    onNavigateToNewPassword: () -> Unit
) {
    val viewModel: OtpVerificationViewModel = viewModel()

    // Создаем производные состояния для значений из ViewModel
    val otpCode by remember { derivedStateOf { viewModel.otpCode } }
    val showOtpError by remember { derivedStateOf { viewModel.showOtpError } }
    val otpError by remember { derivedStateOf { viewModel.otpError } }

    // Для StateFlow используем collectAsState()
    val timerSeconds by viewModel.timerSeconds.collectAsState()
    val isTimerRunning by viewModel.isTimerRunning.collectAsState()
    val hasStartedTyping by viewModel.hasStartedTyping.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val otpState by viewModel.otpState.collectAsState()

    // Устанавливаем email во ViewModel
    LaunchedEffect(email) {
        if (email.isNotBlank()) {
            viewModel.setEmailForVerification(email)
        }
    }

    // Обработка успешной верификации
    LaunchedEffect(otpState) {
        if (otpState is com.example.shoe_store.ui.viewmodel.OtpState.Success) {
            println("✅ OTP успешно верифицирован!")
            onNavigateToNewPassword()
        }
    }

    // Логирование для отладки
    LaunchedEffect(otpCode) {
        println("📱 OTP код изменен: $otpCode")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(getBackground())
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Подтверждение Email",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = getText()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (email.isNotBlank())
                "Мы отправили код подтверждения на:\n$email"
            else
                "Мы отправили код подтверждения\nна ваш email",
            fontSize = 16.sp,
            color = getSubTextDark(),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Код подтверждения",
                fontSize = 16.sp,
                color = getText(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Поле для ввода OTP кода
            OutlinedTextField(
                value = otpCode,
                onValueChange = viewModel::updateOtpCode,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = {
                    Text(
                        text = "Введите 6-значный код",
                        color = getSubTextDark(),
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                isError = showOtpError,
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = getText()
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = getAccent(),
                    unfocusedBorderColor = if (showOtpError) Color.Red else Color(0xFFE0E0E0),
                    focusedContainerColor = getBlock(),
                    unfocusedContainerColor = getBlock(),
                    cursorColor = getAccent(),
                    unfocusedTextColor = getText(),
                    focusedTextColor = getText(),
                    errorBorderColor = Color.Red,
                    errorContainerColor = getBlock()
                ),
                shape = RoundedCornerShape(12.dp)
            )

            // Показываем ошибку если есть
            if (showOtpError && otpError != null) {
                Text(
                    text = otpError ?: "Ошибка",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                )
            }

            // Таймер
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = viewModel.formatTimer(),
                    fontSize = 20.sp,
                    color = when {
                        !hasStartedTyping -> Color(0xFFA0A0A0)
                        timerSeconds > 10 -> getAccent()
                        else -> Color.Red
                    },
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Кнопка для переотправки кода
        if (hasStartedTyping && !isTimerRunning) {
            TextButton(
                onClick = {
                    println("🔄 Запрос на повторную отправку кода")
                    viewModel.resendOtp()
                },
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Отправить код повторно",
                    fontSize = 14.sp,
                    color = getAccent(),
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Индикатор загрузки
        if (isLoading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator(
                    color = getAccent(),
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Проверка кода...",
                    fontSize = 14.sp,
                    color = getSubTextDark()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Подсказка для тестирования
        Text(
            text = "Тестовый код: 123456",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OtpVerificationPreview() {
    OtpVerificationScreen(
        email = "test@example.com",
        onNavigateToNewPassword = {}
    )
}