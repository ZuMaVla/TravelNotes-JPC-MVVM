package ie.setu.travelnotes.ui.components.general

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.travelnotes.navigation.AppDestination
import ie.setu.travelnotes.navigation.AddPlace
import ie.setu.travelnotes.ui.theme.TravelNotesTheme
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterAlt
import ie.setu.travelnotes.navigation.ListPlace


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarProvider(
    currentScreen: AppDestination,
    canNavigateBack: Boolean,
    isUserLoggedIn: Boolean,
    onLoginClick: () -> Unit = {},
    navigateUp: () -> Unit = {},
    onLogoutClick: () -> Unit,
    onFilterClick: (String) -> Unit = {}
)
{
    TopAppBar(
        title = {
            Text(
                text = currentScreen.label,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Button",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            else
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu Button",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )


        },
        actions = {
            if (currentScreen.route == ListPlace.route) {
                IconButton(onClick = { onFilterClick("PRIVATE") }) {
                    Icon(
                        imageVector = Icons.Filled.FilterAlt,
                        contentDescription = "Filter",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            if (isUserLoggedIn) {
                IconButton(onClick = onLogoutClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Logout",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            else{
                IconButton(onClick = onLoginClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Login,
                        contentDescription = "Login",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    TravelNotesTheme {
        TopAppBarProvider(
            AddPlace,
            true,
            true,
            navigateUp = {},
            onLoginClick = {},
            onLogoutClick = {}
        )
    }
}