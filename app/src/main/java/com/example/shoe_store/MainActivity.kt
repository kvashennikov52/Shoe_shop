package com.example.shoe_store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shoe_store.data.navigation.NavigationApp
import com.example.shoe_store.ui.screens.RegisterAccountScreen
import com.example.shoe_store.ui.screens.SignInScreen
import com.example.shoe_store.ui.theme.ShoeShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoeShopTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationApp()
                }
            }
        }
    }
}

// Preview функции для отдельных экранов
@Preview(showBackground = true)
@Composable
fun RegisterAccountPreview() {
    ShoeShopTheme() {
        RegisterAccountScreen(
            onNavigateToSignIn = {},
            onSignUpClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    ShoeShopTheme() {
        SignInScreen(
            onForgotPasswordClick = {},
            onSignInClick = {},
            onSignUpClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview() {
    ShoeShopTheme() {
        NavigationApp()
    }
}