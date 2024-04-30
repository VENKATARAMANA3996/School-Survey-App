package uk.ac.tees.mad.D3574333.routing

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.D3574333.ui.feedback.FeedbackScreen
import uk.ac.tees.mad.D3574333.ui.login.LoginScreen
import uk.ac.tees.mad.D3574333.ui.main.MainScreen
import uk.ac.tees.mad.D3574333.ui.register.RegisterScreen
import uk.ac.tees.mad.D3574333.ui.splash.SplashScreen

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