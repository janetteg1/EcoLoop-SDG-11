package com.example.a211390_ganthaithie_nelson_lab1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: MarketItemListing)

    @Update
    suspend fun update(item: MarketItemListing)

    @Delete
    suspend fun delete(item: MarketItemListing)

    @Query("SELECT * FROM market_item_listings WHERE id = :id")
    fun getItem(id: String): Flow<MarketItemListing>

    @Query("SELECT * FROM market_item_listings ORDER BY itemName ASC")
    fun getAllItems(): Flow<List<MarketItemListing>>
}