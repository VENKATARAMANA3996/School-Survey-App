package com.schoolsurvey.ui.feedback

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.schoolsurvey.R
import com.schoolsurvey.ui.theme.SchoolSurveyAppTheme
import com.schoolsurvey.ui.theme.gray
import com.schoolsurvey.ui.theme.green
import com.schoolsurvey.ui.theme.white
import com.schoolsurvey.utils.RoundedButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FeedbackScreen(navController: NavController) {
    val context = LocalContext.current
    var rate by remember { mutableStateOf(0F) }
    var isBooked by remember { mutableStateOf(false) }
    var review by remember { mutableStateOf("") }
    SchoolSurveyAppTheme {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = green)
                    .padding(top = 40.dp)
            ) {
                SmallTopAppBar(
                    title = {
                        Text(
                            text = "Feedback Screen", color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                tint = Color.White,
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = green,
                        titleContentColor = Color.White
                    )
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize().background(color = white)
                ) {

                    Spacer(Modifier.height(30.dp))
                    Text(
                        "Submit your feedback to School!!!",
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 15.dp),

                        )
                    Spacer(Modifier.height(10.dp))
                    StarRatingBar(
                        maxStars = 10,
                        rating = rate
                    ) {
                        rate = it
                    }
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(start = 15.dp, top = 10.dp, end = 15.dp)
                            .background(Color.White, RoundedCornerShape(5.dp)),
                        shape = RoundedCornerShape(5.dp),
                        value = review,
                        placeholder = {
                            Text("Enter review", color = gray, fontSize = 16.sp)
                        },
                        onValueChange = { review = it },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    RoundedButton(
                        text = "Submit",
                        textColor = white,
                        onClick = {
                            if (rate>0F) {
                                if (review.isNotEmpty()) {
                                    isBooked = true
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please enter review.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please select rate.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        if (isBooked) {
            AlertDialog(
                onDismissRequest = {
                    isBooked = false
                },
                title = { Text(stringResource(id = R.string.app_name)) },
                text = { Text("Your feedback submitted successfully.") },
                confirmButton = {
                    RoundedButton(
                        text = "Ok",
                        textColor = white,
                        onClick = {
                            navController.navigateUp()
                            isBooked = false
                        }
                    )
                },
                dismissButton = {}
            )
        }


    }
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(), horizontalArrangement = Arrangement.Start
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color.Black
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i.toFloat())
                        }
                    )
                    .width(starSize)
                    .height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}