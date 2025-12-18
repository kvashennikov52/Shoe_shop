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
import com.example.shoe_store.ui.screens.OnboardScreen  // Добавляем импорт
import com.example.shoe_store.ui.screens.HomeScreen     // Добавляем импорт
import com.example.shoe_store.ui.screens.ProfileScreen  // Добавляем импорт

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        // ИЗМЕНЕНИЕ: стартовый экран теперь Onboard
        startDestination = Screens.Onboard.route
    ) {
        // ========== ЭКРАН ОНБОРДИНГА ==========
        composable(route = Screens.Onboard.route) {
            OnboardScreen(
                onGetStartedClick = {
                    // Перенаправляем на вход после онбординга
                    navController.navigate(Screens.SignIn.route)
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
                        // Очищаем весь стек до Home
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
                        // Очищаем стек до Home
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
                    // Здесь будет переход на детали товара
                    // navController.navigate("${Screens.ProductDetail.route}/${product.id}")
                },
                onCartClick = {
                    // Переход в корзину
                    // navController.navigate(Screens.Cart.route)
                },
                onSearchClick = {
                    // Логика поиска
                },
                onSettingsClick = {
                    // Переход в профиль
                    navController.navigate(Screens.Profile.route)
                }
            )
        }

        // ========== ЭКРАН ПРОФИЛЯ ==========
        composable(route = Screens.Profile.route) {
            ProfileScreen(
                // Добавьте коллбэки при необходимости, например:
                onBackClick = { navController.popBackStack() },
                onEditProfileClick = {
                    // navController.navigate(Screens.EditProfile.route)
                },
                onLogoutClick = {
                    // Выход и возврат на экран входа
                    navController.navigate(Screens.SignIn.route) {
                        popUpTo(Screens.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

// ========== МАРШРУТЫ ==========
sealed class Screens(val route: String) {
    object Onboard : Screens("onboard")           // Новый маршрут
    object SignIn : Screens("sign_in")
    object RegisterAccount : Screens("register_account")
    object OtpVerification : Screens("otp_verification")
    object ForgotPassword : Screens("forgot_password")
    object Home : Screens("home")
    object Profile : Screens("profile")           // Новый маршрут
    // Можно добавить другие маршруты позже:
    // object ProductDetail : Screens("product_detail/{id}")
    // object Cart : Screens("cart")
    // object EditProfile : Screens("edit_profile")
}