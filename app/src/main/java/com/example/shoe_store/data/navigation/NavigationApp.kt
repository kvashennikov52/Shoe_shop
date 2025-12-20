package com.example.shoe_store.data.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shoe_store.ui.screens.*

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Onboard.route
    ) {
        // Onboard Screen
        composable(route = Screens.Onboard.route) {
            OnboardScreen(
                onGetStartedClick = {
                    navController.navigate(Screens.SignIn.route) {
                        popUpTo(Screens.Onboard.route) { inclusive = true }
                    }
                }
            )
        }

        // Sign In Screen
        composable(route = Screens.SignIn.route) {
            SignInScreen(
                onForgotPasswordClick = {
                    navController.navigate(Screens.ForgotPassword.route)
                },
                onSignInClick = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onSignUpClick = {
                    navController.navigate(Screens.RegisterAccount.route)
                }
            )
        }

        // Register Account Screen
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

        // OTP Verification Screen
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
                    navController.navigate(Screens.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // Forgot Password Screen
        composable(route = Screens.ForgotPassword.route) {
            ForgotPasswordScreen(
                onNavigateBack = { navController.popBackStack() },
                onBackToSignIn = { navController.popBackStack() },
                onResetSuccess = { navController.popBackStack() }
            )
        }

        // Home Screen
        composable(route = Screens.Home.route) {
            HomeScreen(
                onProductClick = { product ->
                    // Navigate to product details
                    // navController.navigate("product_details/${product.id}")
                },
                onCartClick = {
                    // Navigate to cart
                    // navController.navigate(Screens.Cart.route)
                },
                onSearchClick = {
                    // Navigate to search
                    // navController.navigate(Screens.Search.route)
                },
                onSettingsClick = {
                    navController.navigate(Screens.Profile.route)
                },
                onCategoryClick = { categoryName ->
                    navController.navigate("category/${categoryName}")
                }
            )
        }

        // Category Screen
        composable(
            route = "category/{categoryName}",
            arguments = listOf(
                navArgument("categoryName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
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


        // Profile Screen
        composable(route = Screens.Profile.route) {
            ProfileScreen(
                onBackClick = { navController.popBackStack() }
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