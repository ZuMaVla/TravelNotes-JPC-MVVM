package ie.setu.travelnotes.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.travelnotes.firebase.firestore.PlaceModel
//import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.ui.components.general.TopAppBarProvider
import ie.setu.travelnotes.ui.screens.about.AboutScreen
import ie.setu.travelnotes.ui.screens.add.AddScreen
import ie.setu.travelnotes.ui.screens.authentication.AuthScreen
import ie.setu.travelnotes.ui.screens.authentication.AuthViewModel
import ie.setu.travelnotes.ui.screens.details.DetailsScreen
import ie.setu.travelnotes.ui.screens.list.ListScreen
import ie.setu.travelnotes.ui.screens.list.ListViewModel
import ie.setu.travelnotes.ui.screens.map.MapScreen
import ie.setu.travelnotes.ui.screens.map.MapViewModel

@Composable
fun NavHostProvider(
    modifier: Modifier,
    selectedPlace: PlaceModel?,
    navController: NavHostController,
    paddingValues: PaddingValues,
    authViewModel: AuthViewModel,
    listViewModel: ListViewModel,
    mapViewModel: MapViewModel,
    onLoginSuccess: () -> Unit,
    onPlaceUpdateSuccess: () -> Unit,
    onPlaceLongClick: (place: PlaceModel) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Auth.route,
        modifier = Modifier.padding(paddingValues = paddingValues)) {

        composable(route = Auth.route) {
            //call our 'Auth' Screen Here
            AuthScreen(
                modifier = modifier,
                onLoginSuccess = {
                    navController.navigate(ListPlace.route)
                    onLoginSuccess() },
                viewModel = authViewModel,
                mapViewModel = mapViewModel
            )
        }

        composable(route = ListPlace.route) {
            //call our 'List' Screen Here
            ListScreen(
                modifier = modifier,
                selectedPlace = selectedPlace,
                viewModel = listViewModel,
                onPlaceClick = { place ->
                    navController.navigate("details/${place.id}")
                },
                onPlaceLongClick = { place ->
                    onPlaceLongClick(place)
                },
            )
        }

        composable(route = About.route) {
            //call our 'About' Screen Here
            AboutScreen(modifier = modifier)
        }

        composable(route = AddPlace.route) {
            //call our 'Add' Screen Here
            AddScreen(
                modifier = modifier,
                isEdit = false,
                onPlaceUpdateSuccess = { onPlaceUpdateSuccess() },
                navigateUp = { }
            )
        }

        composable(
            route = EditPlace.route,
            arguments = EditPlace.arguments
        ) {
            //call our 'Add' Screen Here
            AddScreen(
                modifier = modifier,
                isEdit = true,
                onPlaceUpdateSuccess = { onPlaceUpdateSuccess() },
                navigateUp = { navController.navigateUp() }
            )
        }

        composable(route = MapPlace.route) {
            //call our 'Map' Screen Here
            MapScreen(
                modifier = modifier,
                viewModel = mapViewModel
            )
        }

        composable(
            route = Details.route,
            arguments = Details.arguments
        ) {
            navBackStackEntry ->
            val placeId = navBackStackEntry.arguments?.getString("placeId")
            //call our 'Details' Screen Here
            DetailsScreen(
                modifier = modifier,
//                placeId = placeId
            )
        }
    }
}
