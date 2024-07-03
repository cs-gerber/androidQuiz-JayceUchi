package edu.uchicago.gerber.androidquiz.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uchicago.gerber.androidquiz.ClassicsQuizApplication
import edu.uchicago.gerber.androidquiz.R
import edu.uchicago.gerber.androidquiz.model.Constants
import edu.uchicago.gerber.androidquiz.model.Constants.ENGLISH_INDEX
import edu.uchicago.gerber.androidquiz.model.Constants.LATIN_INDEX
import edu.uchicago.gerber.androidquiz.model.Constants.PIPE
import edu.uchicago.gerber.androidquiz.model.Constants.GREEK_INDEX
import edu.uchicago.gerber.androidquiz.model.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.random.Random

class QuizViewModel(val mode: String = "latin") : ViewModel(){


    //set all to hard-coded default values initially
    private var _playerName = mutableStateOf("Adam")
    val playerName: State<String> = _playerName

    private var _latinQuestion = mutableStateOf<Question>(Question("Horse", "Equus", "Hippo",
        mutableListOf("Equus", "Annus", "Acqua", "Canis", "Caput")))

    private var _greekQuestion = mutableStateOf<Question>(Question("Horse", "Equus", "Hippo",
        mutableListOf("Hippo", "Etos", "Neró", "Skýlos", "Kefáli")))

    private var _mixedQuestion = mutableStateOf<Question>(Question("Horse", "Equus", "Hippo",
        mutableListOf("Hippo", "Etos", "Neró", "Skýlos", "Kefáli")))

    val latinQuestion: State<Question> = _latinQuestion
    val greekQuestion: State<Question> = _greekQuestion
    val mixedQuestion: State<Question> = _greekQuestion

    private var _questionNumber = mutableStateOf<Int>(1)
    val questionNumber: State<Int> = _questionNumber

    private var _selectedOption = mutableStateOf<String>("Berlin")
    val selectedOption: State<String> = _selectedOption


    //these values used on the ResultScreen
    private var _correctSubmissions = mutableStateOf(92)
    val correctSubmissions: State<Int> = _correctSubmissions

    private var _incorrectSubmissions = mutableStateOf(8)
    val incorrectSubmissions: State<Int> = _incorrectSubmissions



    init {
        //clear out the default values above which are used in Preview mode
        reset()
        clearSelectedOption()
        getQuestion(mode)
    }

    //////////////////////////////////
    //methods for HomeScreen
    //////////////////////////////////
    fun setPlayerName(name: String) {
        _playerName.value = name

    }

    //////////////////////////////////
    //methods for QuestionScreen
    //////////////////////////////////
    //this method will fetch a random item from resources array such as <item>Greece|Athens|EUR</item>
    //and then split and return it as List<String>
    private fun getPipedCountryAndCapital() : List<String> {

        val index: Int = Random.nextInt(Constants.ENG_LAT_GRK_ARRAY.size)
        return Constants.ENG_LAT_GRK_ARRAY[index].split(PIPE)

    }

    fun getQuestion(mode: String) {

        //gets a random country capitals from the array in resources
        val correctAnswer: List<String> = getPipedCountryAndCapital()
        //convert it into a new question object
        val question =
            Question(
                correctAnswer[ENGLISH_INDEX],
                correctAnswer[LATIN_INDEX],
                correctAnswer[GREEK_INDEX]
            )

        val MODE_INDEX = if (mode == "latin"){
            LATIN_INDEX
        }else{
            GREEK_INDEX
        }

        while (question.allAnswers.size < 5) {
            var potentialWrongAnswer = getPipedCountryAndCapital()
            //if any of these conditions are met, go fetch another one
            while (
            //the capital of potentialWrongAnswer is the same (same as correctAnswer), skip
                potentialWrongAnswer[MODE_INDEX] == correctAnswer[MODE_INDEX] ||
                //the wrong answers already contain the potentialWrongAnswer, skip
                question.allAnswers.contains(potentialWrongAnswer[MODE_INDEX])
            ) {
                //go fetch another one
                potentialWrongAnswer = getPipedCountryAndCapital()
            }
            //add the capital of the validated potentialWrongAnswer to the wrong answers of the question
            question.addAnswer(potentialWrongAnswer[MODE_INDEX])
        }
        //add the correct answer
        if (mode == "latin"){
            question.addAnswer(question.latin)
            _latinQuestion.value = question
        }else{
            question.addAnswer(question.greek)
            _greekQuestion.value = question
        }
    }

    fun submitAnswer(question: Question, mode: String) {
        //to update the mutable-state, we first get the value from the state, increment it,
        val nextNumber: Int = questionNumber.value + 1
        //and then set the intermediate variable to the mutable-state
        _questionNumber.value = nextNumber

        val correctAnswer: String = if (mode == "latin"){
            question.latin
        }else{
            question.greek
        }

        //if the user selected the correct answer
        if (correctAnswer == selectedOption.value) {
            incrementCorrect()
        } else {
            incrementIncorrect()
        }
        //queue up another valid question
        getQuestion(mode)
        //clear out the selected value
        clearSelectedOption()
    }

    fun selectOption(option: String) {
        _selectedOption.value = option

    }

    private fun clearSelectedOption() {
        _selectedOption.value = ""
    }

    //////////////////////////////////
    //methods for ResultScreen
    //////////////////////////////////
    fun anotherQuiz() {
        _correctSubmissions.value = 0
        _incorrectSubmissions.value = 0
        _questionNumber.value = 1
    }

    fun reset() {
        anotherQuiz()
        _playerName.value = ""
    }

    private fun incrementCorrect() {
        val correctSubmitted = correctSubmissions.value + 1
        _correctSubmissions.value = correctSubmitted
    }

    private fun incrementIncorrect() {
        val incorrectSubmitted = incorrectSubmissions.value + 1
        _incorrectSubmissions.value = incorrectSubmitted
    }

}