package ie.setu.travelnotes.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ie.setu.travelnotes.ui.screens.about.AboutScreen
import ie.setu.travelnotes.ui.screens.add.AddScreen
import ie.setu.travelnotes.ui.screens.authentication.AuthScreen
import ie.setu.travelnotes.ui.screens.details.DetailsScreen
import ie.setu.travelnotes.ui.screens.list.ListScreen
import ie.setu.travelnotes.ui.screens.map.MapScreen

@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Auth.route,
        modifier = Modifier.padding(paddingValues = paddingValues)) {

        composable(route = Auth.route) {
            //call our 'Auth' Screen Here
            AuthScreen(modifier = modifier)
        }
        composable(route = ListPlace.route) {
            //call our 'List' Screen Here
            ListScreen(modifier = modifier)
        }
        composable(route = About.route) {
            //call our 'About' Screen Here
            AboutScreen(modifier = modifier)
        }
        composable(route = AddPlace.route) {
            //call our 'Add' Screen Here
            AddScreen(modifier = modifier)
        }
        composable(route = MapPlace.route) {
            //call our 'Map' Screen Here
            MapScreen(modifier = modifier)
        }
        composable(route = Details.route) {
            //call our 'Details' Screen Here
            DetailsScreen(modifier = modifier)
        }
    }
}
