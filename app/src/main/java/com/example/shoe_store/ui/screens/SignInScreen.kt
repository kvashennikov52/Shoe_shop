package com.example.shoe_store.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ===== Цвета =====
val Accent = Color(0xFF48B2E7)
val Background = Color(0xFFF8F9FF)
val Block = Color.White
val Text = Color(0xFF333333)
val SubTextDark = Color(0xFF666666)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    onForgotPasswordClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(getBackground())
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        // ===== Заголовок =====
        Text(
            text = "Helloy!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = getText()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Fill your details",
            fontSize = 16.sp,
            color = getSubTextDark()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ===== Email =====
        Text(
            text = "Email",
            fontSize = 16.sp,
            color = getText(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            placeholder = {
                Text("xyz@gmail.com", color = getSubTextDark())
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = getBlock(),
                unfocusedContainerColor = getBlock(),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = getAccent()
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ===== Пароль =====
        Text(
            text = "Password",
            fontSize = 16.sp,
            color = getText(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            placeholder = {
                Text("••••••••", color = getSubTextDark(), fontSize = 20.sp)
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = getSubTextDark()
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = getBlock(),
                unfocusedContainerColor = getBlock(),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = getAccent()
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ===== Восстановить =====
        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.End),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Recovery Password",
                fontSize = 14.sp,
                color = getSubTextDark()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ===== Кнопка Войти =====
        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = getAccent()
            ),
            shape = RoundedCornerShape(16.dp),
            enabled = email.isNotBlank() && password.isNotBlank()
        ) {
            Text(
                text = "Sign In",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(209.dp))

        // ===== Нижний текст  =====
        TextButton(
            onClick = onSignUpClick,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "New User? Create Account",
                fontSize = 16.sp,
                color = getSubTextDark(),
                textAlign = TextAlign.Center
            )
        }
    }
}