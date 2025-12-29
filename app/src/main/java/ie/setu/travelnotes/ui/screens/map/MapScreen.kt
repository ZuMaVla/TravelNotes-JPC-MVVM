package ie.setu.travelnotes.ui.screens.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ie.setu.travelnotes.firebase.firestore.PlaceModel

@Composable
fun MapScreen(modifier: Modifier = Modifier,
              viewModel: MapViewModel) {
    val currentLocation by viewModel.currentLatLng.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 14f)
    }

    LaunchedEffect(currentLocation) {
        cameraPositionState.position =
            CameraPosition.fromLatLngZoom(currentLocation, 14f)
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            compassEnabled = true,
            mapToolbarEnabled = true
        ),
        properties = MapProperties(mapType = MapType.NORMAL)
    ) {
        Marker(
            state = MarkerState(position = currentLocation),
            title = "SETU",
            snippet = "This is SETU"
        )
    }
}