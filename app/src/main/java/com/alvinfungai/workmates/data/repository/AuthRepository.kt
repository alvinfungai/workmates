package com.alvinfungai.workmates.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {
    val authState = callbackFlow {

        val listener = FirebaseAuth.AuthStateListener {
            trySend(it.currentUser)
        }

        auth.addAuthStateListener(listener)

        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }

    suspend fun login(email: String, password: String): FirebaseUser? {
        val result = auth.signInWithEmailAndPassword(email, password).await()

        return result.user
    }
    suspend fun signup(email: String, password: String): FirebaseUser? {
        val result = auth.createUserWithEmailAndPassword(email, password).await()

        return result.user
    }

    fun logout() {
        auth.signOut()
    }
}