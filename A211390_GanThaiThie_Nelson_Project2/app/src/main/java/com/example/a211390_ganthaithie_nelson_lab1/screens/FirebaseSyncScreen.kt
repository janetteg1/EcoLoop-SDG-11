package com.example.a211390_ganthaithie_nelson_lab1.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

private const val FIREBASE_SYNC_TAG = "FirebaseSyncScreen"

@Composable
fun FirebaseSyncScreen() {
    val firestore = remember { FirebaseFirestore.getInstance() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    val testDocument = hashMapOf(
                        "name" to "Test User",
                        "status" to "synced",
                        "timestamp" to Timestamp.now()
                    )

                    firestore.collection("app_users")
                        .add(testDocument)
                        .addOnSuccessListener { documentReference ->
                            Log.d(
                                FIREBASE_SYNC_TAG,
                                "Upload successful. Document ID: ${documentReference.id}"
                            )
                        }
                        .addOnFailureListener { exception ->
                            Log.e(
                                FIREBASE_SYNC_TAG,
                                "Upload failed.",
                                exception
                            )
                        }
                }
            ) {
                Text(text = "Sync to Cloud")
            }

            Text(
                text = "Upload a test document to Firestore",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
