package com.schoolsurvey.ui.login

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.schoolsurvey.R
import com.schoolsurvey.routing.Screen
import com.schoolsurvey.ui.school_preference.SchoolPreference
import com.schoolsurvey.ui.theme.SchoolSurveyAppTheme
import com.schoolsurvey.ui.theme.green
import com.schoolsurvey.ui.theme.white
import com.schoolsurvey.utils.SchoolBorderFeild
import com.schoolsurvey.utils.RoundedButton
import com.schoolsurvey.utils.isValidEmail

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val preference = remember {
        SchoolPreference(context)
    }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var done by remember { mutableStateOf(false) }
    val db = Firebase.firestore
    SchoolSurveyAppTheme {
        Scaffold {
            Column(
                modifier = Modifier.background(green)
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(60.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(5.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_survey),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = "School Survey App", color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = white,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(Modifier.padding(bottom = 10.dp)) {
                        Card(
                            modifier = Modifier.padding(
                                top = 10.dp,
                                start = 10.dp,
                                end = 10.dp
                            ),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(5.dp),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(20.dp)
                            ) {
                                Text(
                                    text = "Login", color = Color.Gray,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    style = TextStyle(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                SchoolBorderFeild(
                                    value = email,
                                    onValueChange = { text ->
                                        email = text
                                    },
                                    placeholder = "Enter email",
                                    keyboardType = KeyboardType.Email,
                                )

                                Spacer(modifier = Modifier.height(5.dp))
                                SchoolBorderFeild(
                                    value = password,
                                    onValueChange = { text ->
                                        password = text
                                    },
                                    placeholder = "Enter Password",
                                    keyboardType = KeyboardType.Password,
                                    visualTransformation = PasswordVisualTransformation(),
                                )

                                Spacer(modifier = Modifier.height(5.dp))
                                RoundedButton(
                                    text = "Login",
                                    onClick = {
                                        if (email.isNotEmpty()) {
                                            if (!isValidEmail(email.toString())) {
                                                if (password.isNotEmpty()) {
                                                    done = true
                                                    db.collection("users")
                                                        .get()
                                                        .addOnSuccessListener { result ->
                                                            if (result.isEmpty) {
                                                                Toast.makeText(
                                                                    context,
                                                                    "User not found.",
                                                                    Toast.LENGTH_LONG
                                                                ).show()
                                                                done = false
                                                                return@addOnSuccessListener
                                                            } else {
                                                                for (document in result) {
                                                                    if (document.data["email"] == email.lowercase() &&
                                                                        document.data["password"] == password
                                                                    ) {
                                                                        preference.saveData(
                                                                            "isLogin",
                                                                            true
                                                                        )
                                                                        Toast.makeText(
                                                                            context,
                                                                            "User Login successfully.",
                                                                            Toast.LENGTH_LONG
                                                                        ).show()
                                                                        navController.navigate(
                                                                            Screen.MainScreen.route
                                                                        ) {
                                                                            popUpTo(Screen.LoginScreen.route) {
                                                                                inclusive = true
                                                                            }
                                                                        }
                                                                        done = false
                                                                    } else if (document.data["email"] == email.lowercase() &&
                                                                        document.data["password"] != password
                                                                    ) {
                                                                        Toast.makeText(
                                                                            context,
                                                                            "Please enter valid password.",
                                                                            Toast.LENGTH_LONG
                                                                        ).show()
                                                                        done = false
                                                                        return@addOnSuccessListener
                                                                    } else {
                                                                        Toast.makeText(
                                                                            context,
                                                                            "User not found.",
                                                                            Toast.LENGTH_LONG
                                                                        ).show()
                                                                        done = false
                                                                        return@addOnSuccessListener
                                                                    }
                                                                }
                                                            }

                                                        }
                                                        .addOnFailureListener { exception ->
                                                            Toast.makeText(
                                                                context,
                                                                exception.message.toString(),
                                                                Toast.LENGTH_LONG
                                                            ).show()
                                                            done = false
                                                        }
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "Please enter password.",
                                                        Toast.LENGTH_LONG
                                                    ).show()

                                                }
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Please enter valid email.",
                                                    Toast.LENGTH_LONG
                                                ).show()

                                            }
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Please enter email.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                )

                            }
                            Spacer(modifier = Modifier.height(20.dp))

                        }

                    }


                }


            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Don't have an account?",
                        textAlign = TextAlign.End,
                        style = TextStyle(color = white)
                    )

                    Text(
                        " Register", modifier = Modifier.clickable {
                            navController.navigate(Screen.RegisterScreen.route)
                        }, textAlign = TextAlign.End,
                        style = TextStyle(color = white)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            if (done) {
                Dialog(
                    onDismissRequest = { },
                    DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .background(white, shape = RoundedCornerShape(8.dp))
                    ) {
                        CircularProgressIndicator(color = green)
                    }
                }
            }

        }
    }
}