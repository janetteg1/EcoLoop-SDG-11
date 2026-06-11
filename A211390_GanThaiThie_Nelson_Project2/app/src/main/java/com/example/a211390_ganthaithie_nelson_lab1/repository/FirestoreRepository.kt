package com.example.a211390_ganthaithie_nelson_lab1.repository

import com.example.a211390_ganthaithie_nelson_lab1.data.CarpoolListing
import com.example.a211390_ganthaithie_nelson_lab1.data.MarketItemListing
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()

    // ── Carpool rides ──────────────────────────────────────────────────────

    suspend fun postRide(listing: CarpoolListing): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val docRef = db.collection("rides").document()
            val rideWithId = listing.copy(id = docRef.id)
            docRef.set(rideWithId.toMap()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getRidesFlow(): Flow<List<CarpoolListing>> = callbackFlow {
        val listener = db.collection("rides")
            .orderBy("postedAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, err ->
                if (err != null) {
                    close(err)
                    return@addSnapshotListener
                }
                if (snap != null) {
                    val rides = snap.documents.mapNotNull {
                        it.toObject(CarpoolListing::class.java)
                    }
                    trySend(rides).isSuccess
                }
            }
        awaitClose { listener.remove() }
    }

    suspend fun deleteRide(id: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            db.collection("rides").document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateSeatsAvailable(id: String, newValue: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                db.collection("rides").document(id).update("seatsAvailable", newValue).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    // ── Marketplace listings ───────────────────────────────────────────────

    suspend fun postListing(listing: MarketItemListing): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val docRef = db.collection("listings").document()
            val listingWithId = listing.copy(id = docRef.id)
            docRef.set(listingWithId.toMap()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getListingsFlow(): Flow<List<MarketItemListing>> = callbackFlow {
        val listener = db.collection("listings")
            .orderBy("itemName", Query.Direction.ASCENDING)
            .addSnapshotListener { snap, err ->
                if (err != null) {
                    close(err)
                    return@addSnapshotListener
                }
                if (snap != null) {
                    val listings = snap.documents.mapNotNull {
                        it.toObject(MarketItemListing::class.java)
                    }
                    trySend(listings).isSuccess
                }
            }
        awaitClose { listener.remove() }
    }

    suspend fun deleteListing(id: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            db.collection("listings").document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateListingCondition(id: String, newCondition: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                db.collection("listings").document(id).update("condition", newCondition).await()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}

// Extension function to convert data class to Map for Firestore
fun CarpoolListing.toMap(): Map<String, Any> = mapOf(
    "id" to id,
    "pickupLocation" to pickupLocation,
    "dropOffLocation" to dropOffLocation,
    "driverName" to driverName,
    "vehicle" to vehicle,
    "plateNumber" to plateNumber,
    "price" to price,
    "seatsAvailable" to seatsAvailable,
    "postedAt" to postedAt,
    "expiresInMinutes" to expiresInMinutes,
    "contactNow" to contactNow,
    "imageUrl" to imageUrl
)

fun MarketItemListing.toMap(): Map<String, Any> = mapOf(
    "id" to id,
    "itemName" to itemName,
    "price" to price,
    "description" to description,
    "condition" to condition,
    "category" to category,
    "location" to location,
    "contactNow" to contactNow,
    "imageUrl" to imageUrl
)
