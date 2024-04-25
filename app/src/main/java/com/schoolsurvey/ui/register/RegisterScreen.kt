package com.schoolsurvey.ui.register

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import com.schoolsurvey.R
import com.schoolsurvey.routing.Screen
import com.schoolsurvey.ui.theme.SchoolSurveyAppTheme
import com.schoolsurvey.utils.SchoolBorderFeild
import com.schoolsurvey.utils.RoundedButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.schoolsurvey.ui.school_preference.SchoolPreference
import com.schoolsurvey.ui.theme.green
import com.schoolsurvey.ui.theme.white
import com.schoolsurvey.utils.isValidEmail

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val preference = remember {
        SchoolPreference(context)
    }
    val scrollState = rememberScrollState()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val db = Firebase.firestore
    var done by remember { mutableStateOf(false) }
    SchoolSurveyAppTheme {
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize().background(green).verticalScroll(scrollState)
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
                    text = "School Survey App", color = white,
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
                                    text = "Register", color = Color.Gray,
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
                                    value = name,
                                    onValueChange = { text ->
                                        name = text
                                    },
                                    placeholder = "Enter name",
                                    keyboardType = KeyboardType.Text,
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
                                    text = "Register",
                                    onClick = {
                                        if (name.isNotEmpty()) {
                                            if (email.isNotEmpty()) {
                                                if (!isValidEmail(email)) {
                                                    if (password.isNotEmpty()) {
                                                        done = true
                                                        val user = hashMapOf(
                                                            "name" to name,
                                                            "email" to email.lowercase(),
                                                            "password" to password
                                                        )
                                                        db.collection("users")
                                                            .get()
                                                            .addOnSuccessListener { result ->
                                                                if (result.isEmpty) {
                                                                    db.collection("users")
                                                                        .add(user)
                                                                        .addOnSuccessListener { documentReference ->
                                                                            preference.saveData(
                                                                                "isLogin",
                                                                                true
                                                                            )
                                                                            Toast.makeText(
                                                                                context,
                                                                                "User Register successfully.",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()
                                                                            navController.navigate(
                                                                                Screen.MainScreen.route
                                                                            ) {
                                                                                popUpTo(Screen.LoginScreen.route) {
                                                                                    inclusive = true
                                                                                }
                                                                            }
                                                                            done = false
                                                                        }
                                                                        .addOnFailureListener { e ->

                                                                            Toast.makeText(
                                                                                context,
                                                                                e.message.toString(),
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()
                                                                            done = false
                                                                        }
                                                                } else {
                                                                    for (document in result) {
                                                                        if (document.data["email"] == email.lowercase() &&
                                                                            document.data["password"] == password
                                                                        ) {
                                                                            Toast.makeText(
                                                                                context,
                                                                                "User Already exists.",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()
                                                                            done = false
                                                                            return@addOnSuccessListener
                                                                        } else {
                                                                            db.collection("users")
                                                                                .add(user)
                                                                                .addOnSuccessListener { documentReference ->
                                                                                    preference.saveData(
                                                                                        "isLogin",
                                                                                        true
                                                                                    )
                                                                                    Toast.makeText(
                                                                                        context,
                                                                                        "User Register successfully.",
                                                                                        Toast.LENGTH_SHORT
                                                                                    ).show()
                                                                                    navController.navigate(
                                                                                        Screen.MainScreen.route
                                                                                    ) {
                                                                                        popUpTo(Screen.RegisterScreen.route) {
                                                                                            inclusive = true
                                                                                        }
                                                                                    }
                                                                                    done = false
                                                                                }
                                                                                .addOnFailureListener { e ->
                                                                                    Toast.makeText(
                                                                                        context,
                                                                                        e.message.toString(),
                                                                                        Toast.LENGTH_SHORT
                                                                                    ).show()
                                                                                    done = false
                                                                                }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            .addOnFailureListener { exception ->
                                                                Toast.makeText(
                                                                    context,
                                                                    exception.message.toString(),
                                                                    Toast.LENGTH_SHORT
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
                                                        "Please enter email.",
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
                                                "Please enter name.",
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
                        "Already have an account?",
                        textAlign = TextAlign.End,
                        style = TextStyle(color = white)
                    )

                    Text(
                        " Login", modifier = Modifier.clickable {
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.RegisterScreen.route) {
                                    inclusive = true
                                }
                            }
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