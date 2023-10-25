package com.example.marsphotos

import android.app.Application
import com.example.marsphotos.data.AppContainer
import com.example.marsphotos.data.DefaultAppContainer

/** AppContainer instance used by the rest of classes to obtain dependencies */

//Declare a variable called container of the type AppContainer to store the DefaultAppContainer object.
// The variable is initialized during the call to onCreate(), so the variable
// needs to be marked with the lateinit modifier.
class MarsPhotosApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}