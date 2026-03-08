package com.alvinfungai.workmates.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alvinfungai.workmates.presentation.viewmodel.AuthViewModel

@Composable
fun HomeScreen(viewModel: AuthViewModel) {
    val user by viewModel.user.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        Text("Logged in as:")
        Text(user?.email ?: "Guest")
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = { viewModel.logout() }
        ) {
            Text("Logout")
        }
    }
}