package com.example.shoe_store.data.navigation

import android.R.attr.text
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
import com.example.shoe_store.ui.screens.OnboardScreen
import com.example.shoe_store.ui.screens.HomeScreen
import com.example.shoe_store.ui.screens.ProfileScreen
import com.example.shoe_store.ui.screens.CategoryScreen

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    text
    NavHost(
        navController = navController,
        startDestination = Screens.Onboard.route
    ) {
        // ========== ЭКРАН ОНБОРДИНГА ==========
        composable(route = Screens.Onboard.route) {
            OnboardScreen(
                onGetStartedClick = {
                    navController.navigate(Screens.SignIn.route) {
                        popUpTo(Screens.Onboard.route) { inclusive = true }
                    }
                }
            )
        }

        // ========== ЭКРАН ВХОДА ==========
        composable(route = Screens.SignIn.route) {
            SignInScreen(
                onForgotPasswordClick = {
                    navController.navigate(Screens.ForgotPassword.route)
                },
                onSignInClick = {
                    println("Вход выполнен успешно")
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.Onboard.route) {
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
                        popUpTo(Screens.Onboard.route) {
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
            HomeScreen(
                onProductClick = { product ->
                    // TODO: Переход на детали товара
                },
                onCartClick = {
                    // TODO: Переход в корзину
                },
                onSearchClick = {
                    // TODO: Логика поиска
                },
                onSettingsClick = {
                    navController.navigate(Screens.Profile.route)
                }
            )
        }
        composable("category/{categoryName}") { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Category"

            CategoryScreen(
                categoryName = categoryName,
                onBackClick = { navController.popBackStack() },
                onProductClick = { /* TODO */ },
                onFavoriteClick = { /* TODO */ },
                onAllClick = {
                    // возвращаемся на home при выборе All
                    navController.popBackStack(route = "home", inclusive = false)
                }
            )
        }
    }
}

sealed class Screens(val route: String) {
    object Onboard : Screens("onboard")
    object SignIn : Screens("sign_in")
    object RegisterAccount : Screens("register_account")
    object OtpVerification : Screens("otp_verification")
    object ForgotPassword : Screens("forgot_password")
    object Home : Screens("home")
    object Profile : Screens("profile")
}