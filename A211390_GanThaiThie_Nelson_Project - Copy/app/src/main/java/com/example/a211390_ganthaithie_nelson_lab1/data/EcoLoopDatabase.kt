package com.example.a211390_ganthaithie_nelson_lab1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CarpoolListing::class,
        MarketItemListing::class,
        ChatMessage::class,
        UserProfile::class
    ],
    version = 1,
    exportSchema = false
)
abstract class EcoLoopDatabase : RoomDatabase() {
    abstract fun carpoolListingDao(): CarpoolListingDao
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var Instance: EcoLoopDatabase? = null

        fun getDatabase(context: Context): EcoLoopDatabase =
            Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    EcoLoopDatabase::class.java,
                    "ecoloop_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
    }
}
