package edu.uchicago.gerber.androidquiz.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import edu.uchicago.gerber.androidquiz.navigation.Screen
import edu.uchicago.gerber.androidquiz.ui.theme.RedColor
import edu.uchicago.gerber.androidquiz.viewmodel.QuizViewModel
import edu.uchicago.gerber.androidquiz.model.Question


@Composable
fun QuestionScreen(navController: NavController, viewModel: QuizViewModel, mode: String = "latin") {


    val selectedOption: String = viewModel.selectedOption.value
    val question: Question = if (mode == "latin"){
        viewModel.latinQuestion.value
    }else{
        viewModel.greekQuestion.value
    }
    val questionNumber: Int = viewModel.questionNumber.value
    val answers: List<String> = if (mode == "latin"){
        viewModel.latinQuestion.value.allAnswers
    }else{
        viewModel.greekQuestion.value.allAnswers
    }



    Scaffold(
        //define the padding at the root
        Modifier.padding(paddingValues = PaddingValues(all = 10.dp)),
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
            paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(7f)
            ) {
                Text(
                    text = "Question $questionNumber :",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                    style = MaterialTheme.typography.subtitle2
                )
                Divider()
                Text(
                    text = question.questionText,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                    style = MaterialTheme.typography.h6
                )
                answers.forEach { option ->
                    Box(modifier = Modifier
                        .padding(2.dp)
                        .selectable(
                            selected = false,
                            onClick = { viewModel.selectOption(option = option) }
                        )) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)

                        ) {
                            RadioButton(
                                selected = selectedOption == option,
                                onClick = { viewModel.selectOption(option = option)})
                            Text(
                                text = option,
                                modifier = Modifier
                                    .padding(top = 12.dp),

                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(12.dp),
            ) {
                Button(
                    onClick = {
                        viewModel.submitAnswer(question = question, mode = mode)
                    },
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxHeight(),
                    enabled = selectedOption.isNotBlank()
                ) {
                    Text(
                        text = "Submit", style = MaterialTheme.typography.button.copy(
                            fontSize = 16.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        navController.popBackStack(Screen.Question.route, true)
                        navController.navigate(Screen.Result.route)
                    },
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = RedColor)

                ) {
                    Text(
                        text = "Quit", style = MaterialTheme.typography.button.copy(
                            fontSize = 16.sp,
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
fun QuestionScreenPreview(quizViewModel: QuizViewModel = QuizViewModel("latin")) {
    QuestionScreen(navController = rememberNavController(), viewModel = quizViewModel, mode = "latin")
}