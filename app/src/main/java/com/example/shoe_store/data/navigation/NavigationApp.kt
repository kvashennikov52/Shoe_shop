package com.example.shoe_store.data.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shoe_store.ui.screens.ForgotPasswordScreen
import com.example.shoe_store.ui.screens.OtpVerificationScreen
import com.example.shoe_store.ui.screens.RegisterAccountScreen
import com.example.shoe_store.ui.screens.SignInScreen

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.SignIn.route
    ) {
        // ========== ЭКРАН ВХОДА ==========
        composable(route = Screens.SignIn.route) {
            SignInScreen(
                onForgotPasswordClick = {
                    navController.navigate(Screens.ForgotPassword.route)
                },
                onSignInClick = {
                    println("Вход выполнен успешно")
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.SignIn.route) {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate(Screens.RegisterAccount.route)
                }
            )
        }

        // ========== ЭКРАН РЕГИСТРАЦИИ ==========
        composable(route = Screens.RegisterAccount.route) {
            RegisterAccountScreen(
                onNavigateToSignIn = {
                    navController.popBackStack()
                },
                onSignUpClick = { email ->
                    // Переходим на OTP верификацию с email
                    navController.navigate("${Screens.OtpVerification.route}/$email")
                }
            )
        }

        // ========== ЭКРАН OTP ВЕРИФИКАЦИИ ==========
        composable(
            route = "${Screens.OtpVerification.route}/{email}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""

            OtpVerificationScreen(
                email = email,
                onNavigateToNewPassword = {
                    println("OTP верификация успешна")
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.SignIn.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // ========== ЭКРАН ВОССТАНОВЛЕНИЯ ПАРОЛЯ ==========
        composable(route = Screens.ForgotPassword.route) {
            ForgotPasswordScreen(
                onNavigateBack = { navController.popBackStack() },
                onBackToSignIn = { navController.popBackStack() },
                onResetSuccess = { navController.popBackStack() }
            )
        }

        // ========== ГЛАВНЫЙ ЭКРАН ==========
        composable(route = Screens.Home.route) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Главный экран",
                    fontSize = 24.sp
                )
            }
        }
    }
}

// ========== МАРШРУТЫ ==========
sealed class Screens(val route: String) {
    object SignIn : Screens("sign_in")
    object RegisterAccount : Screens("register_account")
    object OtpVerification : Screens("otp_verification")
    object ForgotPassword : Screens("forgot_password")
    object Home : Screens("home")
}