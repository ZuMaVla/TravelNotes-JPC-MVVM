package ie.setu.travelnotes.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Details
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

object About : AppDestination {
    override val icon = Icons.Filled.Info
    override val label = "About"
    override val route = "about"
}

object Details : AppDestination {
    override val icon = Icons.Filled.Details
    override val label = "Details"
    override val route = "details"
}

object MapPlace : AppDestination {
    override val icon = Icons.Filled.Map
    override val label = "Map View"
    override val route = "map"
}

val bottomAppBarDestinations = listOf(AddPlace, ListPlace, MapPlace, About)
val allDestinations = listOf(AddPlace, ListPlace, MapPlace, About, Details, Auth)

