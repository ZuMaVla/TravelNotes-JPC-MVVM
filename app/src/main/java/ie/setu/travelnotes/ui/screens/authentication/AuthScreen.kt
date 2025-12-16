package ie.setu.travelnotes.ui.screens.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ie.setu.travelnotes.firebase.auth.Response
import ie.setu.travelnotes.ui.components.auth.AuthScreenText
import ie.setu.travelnotes.ui.components.auth.AuthTextField
import ie.setu.travelnotes.ui.theme.TravelNotesTheme

@Composable
fun AuthScreen(modifier: Modifier = Modifier,
               onLoginSuccess: () -> Unit,
               viewModel: AuthViewModel) {

    val login by viewModel.loginState.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(login) {
        if (login is Response.Success) {
            val isLoginSuccessful = (login as Response.Success).data
            if (isLoginSuccessful) {
                onLoginSuccess()
            }
        }
    }

   Column {
        Column(
            modifier = modifier.padding(
                start = 24.dp,
                end = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AuthScreenText()

            AuthTextField(
                value = viewModel.email,
                onTextChange = { viewModel.email = it },
                label = "Email"
            )
            AuthTextField(
                value = viewModel.password,
                onTextChange = { viewModel.password = it },
                label = "Password"
            )
            if (error != null) {
                Text(error!!)
            }
            if (login is Response.Loading) {
                Box(modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                    Text("Loading...")
                }
            }
            else {
                Button(
                    onClick = { viewModel.login() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Register/Sign In")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    TravelNotesTheme {
        PreviewAuthScreen()
    }
}

@Composable
fun PreviewAuthScreen() {
    Column(
        modifier = Modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AuthScreenText()

        AuthTextField(
            value = "test@example.com",
            onTextChange = {},
            label = "Email"
        )

        AuthTextField(
            value = "123456",
            onTextChange = {},
            label = "Password"
        )
        Button(
            onClick = {  },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register/Sign In")
        }

    }
}

