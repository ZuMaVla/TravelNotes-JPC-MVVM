package ie.setu.travelnotes.ui.screens.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.travelnotes.data.room.Converters
//import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.firebase.firestore.PlaceModel
import ie.setu.travelnotes.data.room.repositories.PlaceRepository
import ie.setu.travelnotes.firebase.firestore.FirestorePlaceRepository
import ie.setu.travelnotes.firebase.firestore.toMillis
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
    val selectedDate: Long = LocalDate.now().toMillis(),
    val id: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null,
    val isSaved: Boolean = false,
    val isEdit: Boolean = false
)

@HiltViewModel
class AddViewModel  @Inject
constructor(
    private val placeRepository: FirestorePlaceRepository,
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private var _uiAddEditPlaceState = MutableStateFlow(UiAddEditPlaceState())
    val uiAddEditPlaceState: StateFlow<UiAddEditPlaceState> = _uiAddEditPlaceState.asStateFlow()
    val placeId: String = savedStateHandle[EditPlace.placeId] ?: ""

    private var originalPlace: PlaceModel? = null

    init {
        getPlace(placeId) //
    }

    fun onNameChange(newName: String) {
        _uiAddEditPlaceState.update { it.copy(placeName = newName) }
    }

    fun onDescriptionChange(newDescription: String) {
        _uiAddEditPlaceState.update { it.copy(placeDescription = newDescription) }
    }

    fun onDateChange(newDate: Long) {
        _uiAddEditPlaceState.update { it.copy(selectedDate = newDate) }
    }

    fun onNavigateAway() {
        // Reset flags when the user navigates away, so events don't re-trigger.
        _uiAddEditPlaceState.update { it.copy(isSaved = false, isError = false, error = null) }
    }


    fun addOrUpdatePlace() {
        viewModelScope.launch {
            val uiState = uiAddEditPlaceState.value
            try {
                _uiAddEditPlaceState.update { it.copy(isLoading = true) }
                val place = originalPlace!!.copy(
                    name = uiState.placeName,
                    description = uiState.placeDescription,
                    dateMillis = (uiState.selectedDate)
                )

                Timber.i("PVM: isEdit = ${uiState.isEdit}")
                if (uiState.isEdit) {
                    placeRepository.update(place, authService.userId)
                }
                else {
                    placeRepository.insert(place, authService.userId)
                }
                _uiAddEditPlaceState.update {
                    it.copy(
                        isLoading = false,
                        isSaved = true,
                        error = null,
                        isError = false
                    )
                }
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
            val defaultPlace = PlaceModel().copy(id = "-1")
            var placeNewOrToEdit = defaultPlace

            if (placeId == null || placeId == "") {
                Timber.i("AEVM Message : will attempt to add new place")
            } else {
                try {
                    placeNewOrToEdit =
                        placeRepository.getPlaceById(placeId) ?: defaultPlace
                    Timber.i("AEVM Message : Name : ${placeNewOrToEdit.name}")
                } catch (e: Exception) {
                    placeNewOrToEdit = defaultPlace
                    Timber.i("AEVM Error Message : ${e.message}")
                } finally {
                    Timber.i("AEVM Message : Name : ${placeNewOrToEdit.name}")
                }
            }
            if (placeNewOrToEdit != defaultPlace) {
                _uiAddEditPlaceState.update { it.copy(isEdit = true) }
            } else {
                _uiAddEditPlaceState.update { it.copy(isEdit = false) }
                placeNewOrToEdit = PlaceModel().copy()
            }
            updatePlaceState(placeNewOrToEdit)
            originalPlace = placeNewOrToEdit.copy()
        }
    }

    private fun updatePlaceState(place: PlaceModel?) {
        var temp = place
        if (temp == null) {
            temp = PlaceModel()
        }
        _uiAddEditPlaceState.update {
            it.copy(
                placeName = temp.name,
                placeDescription = temp.description,
                selectedDate = temp.dateMillis,
                id = temp.id,
                isLoading = false,
                error = null,
                isError = false
            )
        }
    }

    fun onPlaceAdded() {        // To clear place details after saving
        updatePlaceState(place = PlaceModel())
    }

}
