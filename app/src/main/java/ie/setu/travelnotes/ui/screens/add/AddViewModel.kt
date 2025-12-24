package ie.setu.travelnotes.ui.screens.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.data.room.repositories.PlaceRepository
import ie.setu.travelnotes.firebase.services.AuthService
import ie.setu.travelnotes.navigation.EditPlace
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

data class UiAddEditPlaceState(
    val placeName: String = "",
    val placeDescription: String = "",
    val selectedDate: LocalDate = LocalDate.now(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null,
    val isSaved: Boolean = false
)

@HiltViewModel
class AddViewModel  @Inject
constructor(
    private val placeRepository: PlaceRepository,
    private val authService: AuthService,
    private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {
    val userId = authService.userId!!
    private var _uiAddEditPlaceState = MutableStateFlow(UiAddEditPlaceState())
    val uiAddEditPlaceState: StateFlow<UiAddEditPlaceState> = _uiAddEditPlaceState.asStateFlow()

    var isEdit: Boolean
    var currentPlace: PlaceModel = PlaceModel()
    init {
        val placeId: String? = savedStateHandle[EditPlace.placeId]
        if (placeId != null) {
            // If the ID exists, we are in EDIT mode.
            isEdit = true
            Timber.i("AEVM init: Attempting Edit mode. Loading place with ID: $placeId")
        } else {
            // If the ID is null, we are in ADD mode.
            isEdit = false
            Timber.i("AEVM init: In Add mode.")
        }
        getPlace(placeId?: "") //

    }

    fun onNameChange(newName: String) {
        _uiAddEditPlaceState.update { it.copy(placeName = newName) }
    }

    fun onDescriptionChange(newDescription: String) {
        _uiAddEditPlaceState.update { it.copy(placeDescription = newDescription) }
    }

    fun onDateChange(newDate: LocalDate) {
        _uiAddEditPlaceState.update { it.copy(selectedDate = newDate) }
    }

    fun onNavigateAway() {
        // Reset flags when the user navigates away, so events don't re-trigger.
        _uiAddEditPlaceState.update { it.copy(isSaved = false, isError = false, error = null) }
    }

    fun constructPlace(place: PlaceModel): PlaceModel {
        place.name = uiAddEditPlaceState.value.placeName
        place.description = uiAddEditPlaceState.value.placeDescription
        place.date = uiAddEditPlaceState.value.selectedDate
        return place
    }

    fun addOrUpdatePlace() {
        var place = currentPlace.copy()

        place = constructPlace(place)

        viewModelScope.launch {
            try {
                _uiAddEditPlaceState.update { it.copy(isLoading = true) }
                Timber.i("PVM: isEdit = $isEdit")
                if (isEdit) {
                    placeRepository.update(place, userId)
                }
                else {
                    place.userId = userId
                    placeRepository.insert(place, userId)
                }
                _uiAddEditPlaceState.update {
                    it.copy(
                        isLoading = false,
                        isSaved = true,
                        error = null,
                        isError = false
                    )
                }
                _uiAddEditPlaceState.update { it.copy(isError = false)}
            } catch (e: Exception) {
                _uiAddEditPlaceState.update {
                    it.copy(
                        isLoading = false,
                        isSaved = false,
                        error = e.message,
                        isError = true
                    )
                }
                Timber.i("PVM Insert Message : ${e.message}")
            }
        }
    }

    fun getPlace(placeId: String?) {

        viewModelScope.launch {
            _uiAddEditPlaceState.update { it.copy(isLoading = true) }
            try {
                val placeToEdit = if (!placeId.isNullOrEmpty()) {
                    placeRepository.getPlaceById(placeId).firstOrNull()
                } else {
                    null
                }
                if (placeToEdit != null) {
                    currentPlace = placeToEdit
                    isEdit = true
                } else {
                    currentPlace = PlaceModel()
                    isEdit = false
                }

            } catch (e: Exception) {
                currentPlace = PlaceModel()
                isEdit = false
            }
            finally {
                _uiAddEditPlaceState.update {
                    it.copy(
                        placeName = currentPlace.name,
                        placeDescription = currentPlace.description,
                        selectedDate = currentPlace.date,
                        isLoading = false,
                        error = null,
                        isError = false
                    )
                }

                Timber.i("AEVM Message : PlaceID : ${currentPlace.id}")
            }
        }
    }

    fun onPlaceAdded() {
        _uiAddEditPlaceState.update {
            it.copy(
                placeName = "",
                placeDescription = "",
                selectedDate = LocalDate.now()
            )
        }
    }

}
