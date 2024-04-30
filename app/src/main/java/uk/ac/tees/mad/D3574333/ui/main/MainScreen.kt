package uk.ac.tees.mad.D3574333.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.tees.mad.D3574333.R
import uk.ac.tees.mad.D3574333.routing.Screen
import uk.ac.tees.mad.D3574333.ui.drawer.DrawerBody
import uk.ac.tees.mad.D3574333.ui.drawer.DrawerHeader
import uk.ac.tees.mad.D3574333.ui.drawer.TopBar
import uk.ac.tees.mad.D3574333.ui.model.SurveyModel
import uk.ac.tees.mad.D3574333.ui.school_preference.SchoolPreference
import uk.ac.tees.mad.D3574333.ui.theme.SchoolSurveyAppTheme
import uk.ac.tees.mad.D3574333.ui.theme.green
import uk.ac.tees.mad.D3574333.ui.theme.white
import uk.ac.tees.mad.D3574333.utils.GradientButton
import uk.ac.tees.mad.D3574333.utils.RoundedButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    val preference = remember {
        SchoolPreference(context)
    }
    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var isLogout by remember { mutableStateOf(false) }
    var isSubmit by remember { mutableStateOf(false) }
    val list = arrayListOf<SurveyModel>().apply {
        clear()
        add(SurveyModel(question = "Is the information in school newsletters useful and relevant?"))
        add(SurveyModel(question = "Do you find the school’s online portal user-friendly?"))
        add(SurveyModel(question = "Is the homework given to your child age-appropriate?"))
        add(SurveyModel(question = "Do you feel teachers are accessible for discussing your child’s progress?"))
        add(SurveyModel(question = "Are you satisfied with the cleanliness and maintenance of school buildings?"))
        add(SurveyModel(question = "Does the library meet your expectations in terms of resources?"))
        add(SurveyModel(question = "Do you think the school supports children with additional needs well?"))
        add(SurveyModel(question = "Are you aware of the school’s disciplinary procedures?"))
        add(SurveyModel(question = "Do you feel comfortable approaching the school with complaints or concerns?"))
        add(SurveyModel(question = "Is there adequate support for your child’s nutritional needs?"))
    }




    SchoolSurveyAppTheme {
        androidx.compose.material.Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopBar(
                    navController = navController,
                    onNavigationIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                )
            },
            modifier = Modifier.background(color = green),
            drawerContent = {
                DrawerHeader()
                DrawerBody(onFeedback = {
                    navController.navigate(Screen.FeedbackScreen.route)
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                },onLogout = {
                    isLogout = true
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
            },
            backgroundColor = green,
            contentColor = green,
            drawerBackgroundColor = green
        ) { paddingValues ->
            Modifier.padding(
                bottom = paddingValues.calculateBottomPadding()
            )
            Column(
                modifier = Modifier
                    .background(color = green)
                    .padding(top = 40.dp)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    "Participants : 20",
                    fontSize = 14.sp,
                    color = white,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                )
                Spacer(Modifier.height(10.dp))
                Column {
                    list.forEachIndexed { parentIndex, model ->
                        var selectedOption by remember {
                            mutableStateOf("")
                        }
                        Card(
                            modifier = Modifier
                                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                        ) {
                            Spacer(Modifier.height(10.dp))
                            Text(
                                model.question ?: "",
                                fontSize = 14.sp,
                                color = Color.Black,
                                modifier = Modifier
                                    .padding(vertical = 5.dp, horizontal = 10.dp)
                            )
                            Spacer(Modifier.height(10.dp))
                            Divider(
                                thickness = 1.5.dp,
                                color = Color.Gray,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(10.dp))

                            if(selectedOption=="") {
                                Column {
                                    RoundedButton(
                                        text = "Yes",
                                        onClick = {selectedOption = "Yes"})
                                    Spacer(Modifier.width(10.dp))
                                    RoundedButton(
                                        text = "No",
                                        onClick = {selectedOption = "No"})

                                }
                            }else{
                                Text(
                                    "Answer : ${selectedOption ?: ""}",
                                    fontSize = 14.sp,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(vertical = 5.dp, horizontal = 10.dp)
                                )
                            }



                        }
                    }
                }
                GradientButton(
                    onClick = {
                        isSubmit = true
                    },
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth(),
                    textColor = green,
                    isEnabled = true,
                    gradient = Brush.horizontalGradient(listOf(white, white)),
                    text = "Submit the survey"
                )
                Spacer(Modifier.height(10.dp))
            }

        }
        if (isSubmit) {
            AlertDialog(
                onDismissRequest = {
                    isSubmit = false
                },
                title = { Text(stringResource(id = R.string.app_name)) },
                text = { Text("Your survey is successfully submitted.") },
                confirmButton = {
                    RoundedButton(
                        text = "Ok",
                        textColor = white,
                        onClick = {
                            list.apply {
                                clear()
                                add(SurveyModel(question = "Is the information in school newsletters useful and relevant?"))
                                add(SurveyModel(question = "Do you find the school’s online portal user-friendly?"))
                                add(SurveyModel(question = "Is the homework given to your child age-appropriate?"))
                                add(SurveyModel(question = "Do you feel teachers are accessible for discussing your child’s progress?"))
                                add(SurveyModel(question = "Are you satisfied with the cleanliness and maintenance of school buildings?"))
                                add(SurveyModel(question = "Does the library meet your expectations in terms of resources?"))
                                add(SurveyModel(question = "Do you think the school supports children with additional needs well?"))
                                add(SurveyModel(question = "Are you aware of the school’s disciplinary procedures?"))
                                add(SurveyModel(question = "Do you feel comfortable approaching the school with complaints or concerns?"))
                                add(SurveyModel(question = "Is there adequate support for your child’s nutritional needs?"))
                            }
                            isSubmit = false
                        }
                    )
                },
                dismissButton = { }
            )
        }
        if (isLogout) {
            AlertDialog(
                onDismissRequest = {
                    isLogout = false
                },
                title = { Text(stringResource(id = R.string.app_name)) },
                text = { Text("Are you sure you want to logout ?") },
                confirmButton = {
                    RoundedButton(
                        text = "Cancel",
                        textColor = white,
                        onClick = { isLogout = false }
                    )
                },
                dismissButton = {

                    RoundedButton(
                        text = "Logout",
                        textColor = white,
                        onClick = {
                            preference.saveData("isLogin", false)
                            navController.navigate(
                                Screen.LoginScreen.route
                            ) {
                                popUpTo(Screen.MainScreen.route) {
                                    inclusive = true
                                }
                            }
                            isLogout = false
                        }
                    )

                }
            )
        }


    }

}