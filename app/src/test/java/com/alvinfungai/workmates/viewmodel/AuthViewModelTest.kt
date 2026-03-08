package com.alvinfungai.workmates.viewmodel

import com.alvinfungai.workmates.data.repository.AuthRepository
import com.alvinfungai.workmates.presentation.viewmodel.AuthViewModel
import com.alvinfungai.workmates.utils.MainDispatcherRule
import com.google.firebase.auth.FirebaseUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

/**
 * Tests: Repository emits user -> ViewModel collects Flow -> StateFlow updated
 *
 */
@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `login success updates user`() = runTest {
        val repo = mockk<AuthRepository>()
        val user = mockk<FirebaseUser>()

        coEvery { repo.login(any(), any()) } returns user
        every { repo.authState } returns flowOf(user)

        val viewModel = AuthViewModel(repo)

        viewModel.login("test@example.com", "123456")

        advanceUntilIdle() // lets the coroutine launched in viewModelScope finish collecting flow

        coVerify {
            repo.login("test@example.com", "123456")
        }

        assertEquals(user, viewModel.user.value)
    }

    @Test
    fun `login failure does not update user`() = runTest {
        val repo = mockk<AuthRepository>()

        coEvery { repo.login(any(), any()) } throws Exception("Login failed")

        every { repo.authState } returns flowOf(null)

        val viewModel = AuthViewModel(repo)

        viewModel.login("test@example.com", "wrong-passwd")

        advanceUntilIdle()

        coVerify {
            repo.login("test@example.com", "wrong-passwd")
        }

        assertNull(viewModel.user.value)
    }

    @Test
    fun `signup success creates user`() = runTest {
        val repo = mockk<AuthRepository>()
        val user = mockk<FirebaseUser>()

        coEvery { repo.signup(any(), any()) } returns user
        every { repo.authState } returns flowOf(user)

        val viewModel = AuthViewModel(repo)

        viewModel.signup("new-user@example.com", "123456")

        advanceUntilIdle()

        coVerify {
            repo.signup("new-user@example.com", "123456")
        }
        assertEquals(user, viewModel.user.value)
    }

    @Test
    fun `logout calls repo`() = runTest {
        val repo = mockk<AuthRepository>(relaxed = true)

        val viewModel = AuthViewModel(repo)

        viewModel.logout()

        verify {
            repo.logout()
        }
    }

    @Test
    fun `valid user session auto login`() = runTest {
        val repo = mockk<AuthRepository>()
        val user = mockk<FirebaseUser>()

        every { repo.authState } returns flowOf(user)

        val viewModel = AuthViewModel(repo)

        advanceUntilIdle()

        assertEquals(user, viewModel.user.value)
    }
}