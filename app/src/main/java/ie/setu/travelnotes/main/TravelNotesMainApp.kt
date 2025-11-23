package ie.setu.travelnotes.main

import android.app.Application
import timber.log.Timber
import timber.log.Timber.i
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TravelNotesMainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Starting TravelNotes App")
    }
}