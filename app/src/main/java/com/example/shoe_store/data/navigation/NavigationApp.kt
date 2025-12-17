package com.example.shoe_store.data.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoe_store.ui.screens.ForgotPasswordScreen
import com.example.shoe_store.ui.screens.RegisterAccountScreen
import com.example.shoe_store.ui.screens.SignInScreen

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.SignIn.route // Изменили на SignIn как стартовый
    ) {
        // Теперь первым идет экран входа
        composable(route = Screens.SignIn.route) {
            SignInScreen(
                onForgotPasswordClick = {
                    navController.navigate(Screens.ForgotPassword.route)
                },
                onSignInClick = {
                    // После успешного входа
                    // navController.navigate("home")
                },
                onSignUpClick = {
                    // Переход на регистрацию при нажатии "Вы впервые? Создать"
                    navController.navigate(Screens.RegisterAccount.route)
                }
            )
        }

        // Экран регистрации
        composable(route = Screens.RegisterAccount.route) {
            RegisterAccountScreen(
                onNavigateToSignIn = {
                    // Возврат на экран входа
                    navController.popBackStack()
                },
                onSignUpClick = {
                    // После успешной регистрации
                    // navController.navigate("home")
                }
            )
        }

        // Экран восстановления пароля
        composable(route = Screens.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackToSignIn = { navController.popBackStack() },
                onResetSuccess = {}
            )
        }
    }
}

sealed class Screens(val route: String) {
    object SignIn : Screens("sign_in") // Теперь первым
    object RegisterAccount : Screens("register_account") // Вторым
    object ForgotPassword : Screens("forgot_password")
}