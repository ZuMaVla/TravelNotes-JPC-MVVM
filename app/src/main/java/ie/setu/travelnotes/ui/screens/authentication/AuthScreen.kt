package ie.setu.travelnotes.ui.screens.authentication

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import ie.setu.travelnotes.R
import ie.setu.travelnotes.firebase.auth.Response
import ie.setu.travelnotes.ui.components.auth.AuthScreenText
import ie.setu.travelnotes.ui.components.auth.AuthTextField
import ie.setu.travelnotes.ui.screens.map.MapViewModel
//import ie.setu.travelnotes.ui.screens.authentication.drawable
import ie.setu.travelnotes.ui.theme.TravelNotesTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AuthScreen(modifier: Modifier = Modifier,
               onLoginSuccess: () -> Unit,
               viewModel: AuthViewModel,
               mapViewModel: MapViewModel
) {

    val login by viewModel.loginState.collectAsState()
    val error by viewModel.error.collectAsState()
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )


    LaunchedEffect(login) {
        locationPermissions.launchMultiplePermissionRequest()
        if (locationPermissions.allPermissionsGranted) {
            mapViewModel.getLocationUpdates()
        }

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
                        end = 24.dp
                    ),
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
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                            Text("Loading...")
                        }
                    } else {
                        Button(
                            onClick = { viewModel.login() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primary,

                                    )
                        ) {
                            Text("Register/Sign In")
                        }
                        val context = LocalContext.current
                        Button(
                            onClick = { viewModel.signInWithGoogle(context) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primary
                                )
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(1.dp)
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Image(
                                            modifier = Modifier
                                                .padding(end = 30.dp, bottom = 0.dp)
                                                .size(32.dp),
                                            painter = painterResource(
                                                id = R.drawable.ic_google_logo
                                            ),
                                            contentDescription = null
                                        )
                                    }
                                    Text("Register/Sign In with Google")
                                }
                            }
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
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
        ) {
            Text("Register/Sign In")
        }
        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
        ) {
            Box(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth()
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column {
                        Image(
                            modifier = Modifier
                                .padding(end = 30.dp, bottom = 0.dp)
                                .size(32.dp),
                            painter = painterResource(
                                id = R.drawable.ic_google_logo
                            ),
                            contentDescription = null
                        )
                    }
                    Text("Register/Sign In with Google")
                }
            }
        }
    }
}

