package com.schoolsurvey.routing

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.schoolsurvey.ui.feedback.FeedbackScreen
import com.schoolsurvey.ui.login.LoginScreen
import com.schoolsurvey.ui.main.MainScreen
import com.schoolsurvey.ui.register.RegisterScreen
import com.schoolsurvey.ui.splash.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.FeedbackScreen.route) {
            FeedbackScreen(navController = navController)
        }

    }

}