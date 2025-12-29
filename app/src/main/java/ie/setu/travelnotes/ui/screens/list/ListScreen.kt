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
import ie.setu.travelnotes.firebase.firestore.PlaceModel
//import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.navigation.Auth
import ie.setu.travelnotes.navigation.Details
import ie.setu.travelnotes.navigation.ListPlace
import ie.setu.travelnotes.navigation.allDestinations
import ie.setu.travelnotes.ui.components.list.PlaceList
import ie.setu.travelnotes.ui.components.list.PlaceListHeader
import ie.setu.travelnotes.ui.screens.authentication.AuthViewModel
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(modifier: Modifier = Modifier,
               selectedPlace: PlaceModel?,
               viewModel: ListViewModel,
               navController: NavHostController = rememberNavController(),
               onPlaceClick: (PlaceModel) -> Unit,
               onPlaceLongClick: (PlaceModel) -> Unit
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen =
        allDestinations.find { it.route == currentDestination?.route } ?: Auth
    val uiListPlaceState = viewModel.uiPlacesState.collectAsState().value

    val places = uiListPlaceState.placesToDisplay
    val context = LocalContext.current


    LaunchedEffect(uiListPlaceState.userId) {
        if (uiListPlaceState.userId.isNotEmpty()) {
            viewModel.getPlaces(uiListPlaceState.userId)
        Timber.i("LVM: ListScreen LaunchedEffect, userId: ${uiListPlaceState.userId}")
        }
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
            onPlaceClick = { onPlaceClick(it) },
            onPlaceLongClick =
                if (uiListPlaceState.userId == "guest") {
                    {}
                } else {
                    { clickedPlace ->
                        if (clickedPlace.userId == uiListPlaceState.userId) {
                            onPlaceLongClick(clickedPlace)
                        }
                    }
                },
            modifier = modifier,
            onRefreshList = {  },
            currentUserId = uiListPlaceState.userId
        )
    }}