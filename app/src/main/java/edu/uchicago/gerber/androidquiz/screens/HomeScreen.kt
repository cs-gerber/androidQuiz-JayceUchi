package edu.uchicago.gerber.androidquiz.screens


import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.uchicago.gerber.androidquiz.navigation.Screen
import edu.uchicago.gerber.androidquiz.ui.theme.RedColor
import edu.uchicago.gerber.androidquiz.viewmodel.QuizViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: QuizViewModel) {
    val playerName = viewModel.playerName
    val activity = (LocalContext.current as Activity)
    val focusManager = LocalFocusManager.current

    Scaffold(
        //define the padding at the root
        modifier = Modifier.padding(paddingValues = PaddingValues(all = 10.dp)),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Classics Quiz",
                        textAlign = TextAlign.Start,
                    )
                },
            )
        }) {
        //pass the paddingValues from Scaffold into the children to be used
            paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome to Classics Quiz",
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(40.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Your Name",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.body1
                )
                OutlinedTextField(
                    value = playerName.value,
                    onValueChange = { theValue -> viewModel.setPlayerName(theValue) },
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {focusManager.clearFocus()}
                    ),
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.height(100.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        navController.navigate(Screen.Question.route)
                        //Toast.makeText(activity, viewModel.playerName.value, Toast.LENGTH_LONG).show()

                    },
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Start Quiz",
                        style = MaterialTheme.typography.button.copy(
                            fontSize = 24.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        activity.finish()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Green)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = RedColor)
                ) {
                    Text(
                        text = "Exit", style = MaterialTheme.typography.button.copy(
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(quizViewModel: QuizViewModel = QuizViewModel()) {
    HomeScreen(navController = rememberNavController(), viewModel = quizViewModel)
}