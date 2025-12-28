package ie.setu.travelnotes.ui.screens.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.travelnotes.firebase.firestore.PlaceModel
//import ie.setu.travelnotes.data.room.repositories.PlaceRepository
import ie.setu.travelnotes.firebase.firestore.FirestorePlaceRepository
import ie.setu.travelnotes.firebase.services.AuthService
import ie.setu.travelnotes.ui.screens.getAvgRating
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class UiPlacesState(
    val places: List<PlaceModel> = emptyList(),
    val placesToDisplay: List<PlaceModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorBody: Exception? = null,
    val isError: Boolean = false
)

@HiltViewModel
class ListViewModel  @Inject
constructor(private val placeRepository: FirestorePlaceRepository,
            private val authService: AuthService) : ViewModel() {

    private val _uiPlacesState = MutableStateFlow(UiPlacesState())
    val uiPlacesState: StateFlow<UiPlacesState> = _uiPlacesState.asStateFlow()

    init {
        viewModelScope.launch {
            authService.authStateFlow.collect { currentUser ->
                    getPlaces()
            }
        }
    }

    fun getPlaces() {
        viewModelScope.launch {
            _uiPlacesState.update { it.copy(isLoading = true) }
            try {
                placeRepository.getAllByUser(authService.userId)
                    .collect { placesList ->
                        _uiPlacesState.update {
                            it.copy(
                                places = placesList,
                                placesToDisplay = placesList,
                                isLoading = false,
                                errorBody = null,
                                isError = false
                            )
                        }
                        Timber.i("LVM: List updated, size: ${placesList.size} for user: ${authService.userId}")
                    }
            } catch (e: Exception) {
                _uiPlacesState.update {
                    it.copy(
                        places = emptyList(),
                        placesToDisplay = emptyList(),
                        isLoading = false,
                        errorBody = e,
                        isError = true
                    )
                }
                val uiState = _uiPlacesState.value
                Timber.e("LVM error message : ${uiState.errorBody?.message}")
            }
        }
    }

    fun deletePlace(place: PlaceModel) {
        viewModelScope.launch {
            _uiPlacesState.update { it.copy(isLoading = true) }
            try {
                placeRepository.delete(place, authService.currentUser?.uid!!)
                Timber.i("LVM: Place deleted, id=${place.id}")
            } catch (e: Exception) {
                _uiPlacesState.update {
                    it.copy(
                        isLoading = false,
                        errorBody = e,
                        isError = true
                    )
                }
                val uiState = _uiPlacesState.value
                Timber.e("LVM error deleting message : ${uiState.errorBody?.message}")
            }
            _uiPlacesState.update { it.copy(isLoading = false) }
        }
    }

    fun onSortChanged(sortOption: String) {
        Timber.i("LVM: Sort option changed to $sortOption")
        when (sortOption) {
            "NAME" -> {
                _uiPlacesState.update {
                    it.copy(
                        places = it.places.sortedBy { place -> place.name }
                    )
                }
            }
            "DATE" -> {
                _uiPlacesState.update {
                    it.copy(
                        places = it.places.sortedBy { place -> place.dateMillis }
                    )
                }
            }
            "RATING" -> {
                _uiPlacesState.update {
                    it.copy(
                        places = it.places.sortedBy { place -> getAvgRating(place.rating) }
                            .reversed()
                    )
                }
            }
            else -> {}
        }
    }

    fun onFilterChanged(filterOption: String) {
        Timber.i("LVM: Filter option changed to $filterOption")
        when (filterOption) {
            "PUBLIC" -> {
                _uiPlacesState.update {
                    it.copy(
                        placesToDisplay = it.places.filter { place -> place.public }
                    )
                }
            }
            "PRIVATE" -> {
                _uiPlacesState.update {
                    it.copy(
                        placesToDisplay = it.places.filter { place -> place.userId == authService.userId }
                    )
                }
            }
            "AVAILABLE" -> {
                _uiPlacesState.update {
                    it.copy(
                        placesToDisplay = it.places
                    )
                }
            }
            else -> {}
}

    }
    fun isLoggedIn() = authService.isUserAuthInFBase


}