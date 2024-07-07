package edu.uchicago.gerber.androidquiz

import android.app.Application

class AndroidQuizApplication : Application(){

    //create a public static var called app
    companion object {
        lateinit var app: AndroidQuizApplication
            private set
    }

    //this Application (which is a context and therefore can talk to the op-system) is
    //instantiated when the app starts and is available through the entire app life-cycle
    //This is useful for getting access to the res directory, or calling intents.
    override fun onCreate() {
        super.onCreate()
        app = this
    }
}
