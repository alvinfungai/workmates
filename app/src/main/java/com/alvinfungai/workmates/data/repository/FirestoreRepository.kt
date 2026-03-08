package com.alvinfungai.workmates.data.repository

import com.alvinfungai.workmates.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirestoreRepository @Inject constructor(
    private val db: FirebaseFirestore
) {
    suspend fun createUser(userId: String, user: User) {
        db.collection("users")
            .document(userId)
            .set(user)
            .await()
    }

    fun getUser(userId: String): Flow<User?> = callbackFlow {
        val listener = db.collection("users")
            .document(userId)
            .addSnapshotListener { snapshot, _ ->
                val user = snapshot?.toObject(User::class.java)
                trySend(user)
            }
        awaitClose { listener.remove() }
    }
}