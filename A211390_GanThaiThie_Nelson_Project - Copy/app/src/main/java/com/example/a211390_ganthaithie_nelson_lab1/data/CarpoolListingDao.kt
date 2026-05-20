package com.example.a211390_ganthaithie_nelson_lab1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CarpoolListingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ride: CarpoolListing)

    @Update
    suspend fun update(ride: CarpoolListing)

    @Delete
    suspend fun delete(ride: CarpoolListing)

    @Query("SELECT * FROM carpool_listings WHERE id = :id")
    fun getRide(id: String): Flow<CarpoolListing>

    @Query("SELECT * FROM carpool_listings ORDER BY pickupLocation ASC")
    fun getAllRides(): Flow<List<CarpoolListing>>
}