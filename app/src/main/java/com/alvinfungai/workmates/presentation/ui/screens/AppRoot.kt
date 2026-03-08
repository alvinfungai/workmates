package com.alvinfungai.workmates.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alvinfungai.workmates.presentation.viewmodel.AuthViewModel

@Composable
fun AppRoot(modifier: Modifier = Modifier, viewModel: AuthViewModel = hiltViewModel()) {
    val user by viewModel.user.collectAsStateWithLifecycle()

    if (user != null) {
        HomeScreen(viewModel)
    } else {
        AuthScreen(viewModel)
    }
}