package com.example.a211390_ganthaithie_nelson_lab1.data

import android.content.Context

/**
 * Simple container to hold app-wide dependencies (Room database + repository).
 */
class AppContainer(context: Context) {
    val database: EcoLoopDatabase = EcoLoopDatabase.getDatabase(context.applicationContext)
    val repository: EcoLoopRepository = OfflineEcoLoopRepository(
        database.carpoolListingDao(),
        database.itemDao()
    )
}
