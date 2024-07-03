package edu.uchicago.gerber.androidquiz.model

import edu.uchicago.gerber.androidquiz.ClassicsQuizApplication
import edu.uchicago.gerber.androidquiz.R

object Constants {

    //because we define this as "object" this is now a singleton and all of its members are static
    const val PIPE = "|"
    const val ENGLISH_INDEX = 0
    const val LATIN_INDEX = 1
    const val GREEK_INDEX = 2

    val ENG_LAT_GRK_ARRAY = ClassicsQuizApplication.app.resources.getStringArray(R.array.classic_words)
}