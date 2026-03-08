package com.alvinfungai.workmates.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvinfungai.workmates.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> = _user

    init {
        viewModelScope.launch {
            repo.authState.collect {
                _user.value = it
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                repo.login(email, password)
            } catch (e: Exception) {
                // update error state
            }
        }
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            try {
                repo.signup(email, password)
            } catch (e: Exception) {
                // update error state
            }
        }
    }

    fun logout() {
        repo.logout()
    }
}