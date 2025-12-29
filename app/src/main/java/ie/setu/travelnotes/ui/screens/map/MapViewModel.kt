package ie.setu.travelnotes.ui.screens.map

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.travelnotes.data.room.repositories.PlaceRepository
import ie.setu.travelnotes.firebase.firestore.PlaceModel
import ie.setu.travelnotes.location.LocationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel  @Inject
constructor(private val placeRepository: PlaceRepository,
            private val locationTracker: LocationService
) : ViewModel() {

    private val _currentLatLng = MutableStateFlow(LatLng(
        PlaceModel().lat,PlaceModel().lng))
    val currentLatLng: StateFlow<LatLng> = _currentLatLng
    //    get() = _currentLatLng

    private val _hasPermissions = MutableStateFlow(false)
    val hasPermissions: StateFlow<Boolean> get() = _hasPermissions

    fun setPermissions(permissions: Boolean) {
        _hasPermissions.value = permissions
    }

    private fun setCurrentLatLng(latLng: LatLng) {
        _currentLatLng.value = latLng
    }

    fun getLocationUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
            locationTracker.getLocationFlow()
                .collect {
                    it?.let { location ->
                        setCurrentLatLng(
                            LatLng(location.latitude,
                                location.longitude)
                        )
                    }
                }
        }
    }
}
