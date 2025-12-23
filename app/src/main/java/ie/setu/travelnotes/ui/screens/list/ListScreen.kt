package ie.setu.travelnotes.ui.screens.list

import android.widget.Toast
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ie.setu.travelnotes.MainActivity
import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.navigation.Auth
import ie.setu.travelnotes.navigation.Details
import ie.setu.travelnotes.navigation.ListPlace
import ie.setu.travelnotes.navigation.allDestinations
import ie.setu.travelnotes.ui.components.list.PlaceList
import ie.setu.travelnotes.ui.components.list.PlaceListHeader
import ie.setu.travelnotes.ui.screens.authentication.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(modifier: Modifier = Modifier,
               selectedPlace: PlaceModel?,
               viewModel: ListViewModel = hiltViewModel(),
               navController: NavHostController = rememberNavController(),
               onPlaceClick: (PlaceModel) -> Unit,
               onPlaceLongClick: (PlaceModel) -> Unit
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen =
        allDestinations.find { it.route == currentDestination?.route } ?: Auth
    var startScreen = currentBottomScreen

    val currentUser = viewModel.currentUser
    val isActiveSession = viewModel.isLoggedIn()
    val userEmail = if (isActiveSession) currentUser?.email else ""
    val userId = if (isActiveSession) currentUser?.uid else ""
    val places = viewModel.uiPlaces.collectAsState().value
    val context = LocalContext.current


//    val userDestinations = if (!isActiveSession)
//        userSignedOutDestinations()
//    else userSignedInDestinations()

    LaunchedEffect(Unit) {
        viewModel.getPlaces()
    }
    Column(
        modifier = modifier.padding(
            start = 4.dp,
            end = 4.dp
        ),
    ) {
        PlaceListHeader(
            modifier = modifier.padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 6.dp
            )
        )
        PlaceList(
            places = places,
            selectedPlace = selectedPlace,
            onPlaceClick = { navController.navigate(Details.route) },
            onPlaceLongClick = {clickedPlace -> onPlaceLongClick(clickedPlace)
                val messageToDisplay = "Long Click on place ID: ${clickedPlace.id}"
                Toast.makeText(context, messageToDisplay, Toast.LENGTH_LONG).show()
            },
            modifier = modifier,
            onRefreshList = { viewModel.getPlaces() }
        )
    }
}