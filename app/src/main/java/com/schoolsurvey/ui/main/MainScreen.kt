package com.schoolsurvey.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.schoolsurvey.routing.Screen
import com.schoolsurvey.ui.model.SurveyModel
import com.schoolsurvey.ui.theme.SchoolSurveyAppTheme
import com.schoolsurvey.ui.theme.green
import com.schoolsurvey.ui.theme.white
import com.schoolsurvey.utils.RoundedButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    val checked = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val list = arrayListOf<SurveyModel>().apply {
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
        Scaffold {
            Column(
                modifier = Modifier
                    .background(color = green)
                    .padding(top = 40.dp)
                    .verticalScroll(scrollState)
            ) {
                SmallTopAppBar(
                    title = {
                        Text(
                            text = "Quiz", color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                        )
                    },

                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = green,
                        titleContentColor = Color.White
                    )
                )
                Spacer(Modifier.height(10.dp))

                Column {
                    list.forEachIndexed { parentIndex, quizModel ->
                        Card(
                            modifier = Modifier
                                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                                .fillMaxWidth()
                                .height(220.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                        ) {
                            Spacer(Modifier.height(10.dp))
                            Text(
                                quizModel.question ?: "",
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
                            var selectedOption by remember {
                                mutableStateOf("")
                            }
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
                Spacer(Modifier.height(10.dp))
            }

        }


    }

}