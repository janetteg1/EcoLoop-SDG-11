package com.example.a211390_ganthaithie_nelson_lab1.data

import kotlinx.coroutines.flow.Flow

interface EcoLoopRepository {
    fun getAllRidesStream(): Flow<List<CarpoolListing>>
    fun getAllItemsStream(): Flow<List<MarketItemListing>>
    suspend fun insertRide(ride: CarpoolListing)
    suspend fun insertItem(item: MarketItemListing)
    suspend fun deleteRide(ride: CarpoolListing)
    suspend fun deleteItem(item: MarketItemListing)
}

class OfflineEcoLoopRepository(
    private val carpoolListingDao: CarpoolListingDao,
    private val itemDao: ItemDao
) : EcoLoopRepository {
    
    override fun getAllRidesStream(): Flow<List<CarpoolListing>> =
        carpoolListingDao.getAllRides()

    override fun getAllItemsStream(): Flow<List<MarketItemListing>> =
        itemDao.getAllItems()

    override suspend fun insertRide(ride: CarpoolListing) =
        carpoolListingDao.insert(ride)

    override suspend fun insertItem(item: MarketItemListing) =
        itemDao.insert(item)

    override suspend fun deleteRide(ride: CarpoolListing) =
        carpoolListingDao.delete(ride)

    override suspend fun deleteItem(item: MarketItemListing) =
        itemDao.delete(item)
}
