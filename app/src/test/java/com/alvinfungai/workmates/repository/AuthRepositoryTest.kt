package com.alvinfungai.workmates.repository

import app.cash.turbine.test
import com.alvinfungai.workmates.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryTest {


    @Test
    fun `authState emits values`() = runTest {
        val repo = mockk<AuthRepository>()
        val user = mockk<FirebaseUser>()

        every { repo.authState } returns flow {
            emit(null)
            emit(user)
        }

        repo.authState.test {
            Assert.assertNull(awaitItem())
            Assert.assertEquals(user, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}