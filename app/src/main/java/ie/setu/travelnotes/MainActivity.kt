package ie.setu.travelnotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import ie.setu.travelnotes.ui.theme.TravelNotesTheme
import dagger.hilt.android.AndroidEntryPoint
import ie.setu.travelnotes.firebase.auth.Response
import ie.setu.travelnotes.navigation.Auth
import ie.setu.travelnotes.navigation.ListPlace
import ie.setu.travelnotes.navigation.NavHostProvider
import ie.setu.travelnotes.navigation.allDestinations
import ie.setu.travelnotes.ui.components.general.BottomAppBarProvider
import ie.setu.travelnotes.ui.components.general.TopAppBarProvider
import ie.setu.travelnotes.ui.screens.authentication.AuthViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            TravelNotesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TravelNotesApp(modifier = Modifier)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TravelNotesApp(modifier: Modifier = Modifier,
                   navController: NavHostController = rememberNavController(),
                   authViewModel: AuthViewModel = hiltViewModel()) {

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen =
        allDestinations.find { it.route == currentDestination?.route } ?: ListPlace
    val loginState by authViewModel.loginState.collectAsState()
    val isUserLoggedIn = loginState is Response.Success && (loginState as Response.Success).data

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarProvider(
                currentScreen = currentBottomScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                isUserLoggedIn = isUserLoggedIn,
                navigateUp = { navController.navigateUp() },
                onLoginClick = {
                    // Navigate to your Login route here
                    navController.navigate(Auth.route)
                },
                onLogoutClick = {
                    authViewModel.signOut()
                    navController.navigate(ListPlace.route)
                },
            )
        },
        content = { paddingValues ->
            NavHostProvider(
                modifier = modifier,
                navController = navController,
                paddingValues = paddingValues,
                viewModel = authViewModel
            )
        },
        bottomBar = {
            BottomAppBarProvider(navController,
                currentScreen = currentBottomScreen,)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    TravelNotesTheme {
        TravelNotesApp(modifier = Modifier)
    }
}