package ie.setu.travelnotes.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppDestination {
    val icon: ImageVector
    val label: String
    val route: String
}

object Auth : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.Login
    override val label = "Log In"
    override val route = "auth"
}

object ListPlace : AppDestination {
    override val icon = Icons.AutoMirrored.Filled.List
    override val label = "Place List"
    override val route = "list"
}

object AddPlace : AppDestination {
    override val icon = Icons.Filled.AddCircle
    override val label = "Add Place"
    override val route = "add"
}

object EditPlace : AppDestination {
    override val icon = Icons.Filled.Edit
    override val label = "Edit Place"
    const val placeId = "placeId"
    override val route = "edit/{$placeId}"
    val arguments = listOf(navArgument(placeId) { type = NavType.StringType })
}

object DeletePlace : AppDestination {
    override val icon = Icons.Filled.Delete
    override val label = "Delete Place"
    const val placeId = "placeId"
    override val route = "delete/{$placeId}"
    val arguments = listOf(navArgument(placeId) { type = NavType.StringType })
}

object About : AppDestination {
    override val icon = Icons.Filled.Info
    override val label = "About"
    override val route = "about"
}

object Details : AppDestination {
    override val icon = Icons.Filled.Details
    override val label = "Details"
    const val placeId = "placeId"
    override val route = "details/{$placeId}"
    val arguments = listOf(navArgument(placeId) { type = NavType.StringType })
}

object MapPlace : AppDestination {
    override val icon = Icons.Filled.Map
    override val label = "Map View"
    override val route = "map"
}

val bottomAppBarDestinations = listOf(AddPlace, ListPlace, MapPlace, About)
val bottomAppBarDestinationsGuest = listOf(ListPlace, MapPlace, About, Auth)
val allDestinations = listOf(AddPlace, EditPlace, ListPlace, MapPlace, About, Details, Auth)

