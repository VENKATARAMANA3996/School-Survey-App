package com.schoolsurvey.ui.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.schoolsurvey.R
import com.schoolsurvey.routing.Screen
import com.schoolsurvey.ui.school_preference.SchoolPreference
import com.schoolsurvey.ui.theme.SchoolSurveyAppTheme
import com.schoolsurvey.ui.theme.green
import com.schoolsurvey.ui.theme.white
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val preference = remember {
        SchoolPreference(context)
    }
    LaunchedEffect(Unit) {
        delay(3.seconds)
        if(preference.getData("isLogin")) {
            navController.navigate(Screen.MainScreen.route) {
                popUpTo(Screen.SplashScreen.route) {
                    inclusive = true
                }

            }
        }else{
            navController.navigate(Screen.LoginScreen.route) {
                popUpTo(Screen.SplashScreen.route) {
                    inclusive = true
                }
            }
        }

    }
    SchoolSurveyAppTheme {
        Scaffold {
            Column(modifier = Modifier.fillMaxSize().background(green), verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_survey),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.width(200.dp).height(200.dp).align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "School Survey App", color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth().align(Alignment.CenterHorizontally)
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}