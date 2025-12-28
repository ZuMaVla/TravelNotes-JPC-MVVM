package ie.setu.travelnotes.ui.screens.add

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
//import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.firebase.firestore.PlaceModel
import ie.setu.travelnotes.firebase.firestore.FirestorePlaceRepository
import ie.setu.travelnotes.firebase.firestore.toMillis
import ie.setu.travelnotes.firebase.services.AuthService
import ie.setu.travelnotes.firebase.services.StorageService
import ie.setu.travelnotes.navigation.EditPlace
import ie.setu.travelnotes.ui.screens.UiPlaceState
import ie.setu.travelnotes.ui.screens.getAvgRating
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject



@HiltViewModel
class AddViewModel  @Inject
constructor(
    private val placeRepository: FirestorePlaceRepository,
    private val storageService: StorageService,
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private var _uiPlaceState = MutableStateFlow(UiPlaceState())
    val uiPlaceState: StateFlow<UiPlaceState> = _uiPlaceState.asStateFlow()
    val placeId: String = savedStateHandle[EditPlace.placeId] ?: ""

    private var originalPlace: PlaceModel? = null

    init {
        getPlace(placeId) //
    }

    fun onNameChange(newName: String) {
        _uiPlaceState.update { it.copy(placeName = newName) }
    }

    fun onDescriptionChange(newDescription: String) {
        _uiPlaceState.update { it.copy(placeDescription = newDescription) }
    }

    fun onDateChange(newDate: Long) {
        _uiPlaceState.update { it.copy(selectedDate = newDate) }
    }

    fun onNavigateAway() {
        // Reset flags when the user navigates away, so events don't re-trigger.
        _uiPlaceState.update { it.copy(isSaved = false, isError = false, error = null) }
    }


    fun addOrUpdatePlace() {
        viewModelScope.launch {
            val uiState = uiPlaceState.value
            try {
                _uiPlaceState.update { it.copy(isLoading = true) }
                val place = originalPlace!!.copy(
                    name = uiState.placeName,
                    description = uiState.placeDescription,
                    dateMillis = uiState.selectedDate,
                    imageUris = uiState.imageUris,
                    imageToDisplay = uiState.imageToDisplay,
                    rating = uiState.rating,
                    public = uiState.public
                )

                Timber.i("PVM: isEdit = ${uiState.isEdit}")
                if (uiState.isEdit) {
                    placeRepository.update(place, authService.userId)
                }
                else {
                    placeRepository.insert(place, authService.userId)
                }
                _uiPlaceState.update {
                    it.copy(
                        isLoading = false,
                        isSaved = true,
                        error = null,
                        isError = false
                    )
                }
            } catch (e: Exception) {
                _uiPlaceState.update {
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
            _uiPlaceState.update { it.copy(isLoading = true) }
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
                _uiPlaceState.update { it.copy(isEdit = true) }
            } else {
                _uiPlaceState.update { it.copy(isEdit = false) }
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
        _uiPlaceState.update {
            it.copy(
                placeName = temp.name,
                placeDescription = temp.description,
                selectedDate = temp.dateMillis,
                id = temp.id,
                imageUris = temp.imageUris,
                imageToDisplay = temp.imageToDisplay,
                rating = temp.rating,
                avgRating = getAvgRating(temp.rating),
                public = temp.public,
                isLoading = false,
                error = null,
                isError = false
            )
        }
    }

    fun onPlaceAdded() {        // To clear place details after saving
        updatePlaceState(place = PlaceModel())
    }

    fun onPhotoUriChange(uri: Uri) {
        viewModelScope.launch {
            _uiPlaceState.update { it.copy(isLoading = true) }
            val externalUri = uploadCustomPhotoUri(uri).toString()
            Timber.i("PVM: externalUri: $externalUri")

            _uiPlaceState.update { state ->
                state.copy(
                    imageToDisplay = externalUri,
                    imageUris = state.imageUris + externalUri,
                    isLoading = false
                )
            }
        }
    }

    fun onPublicChange(newPublic: Boolean) {
        _uiPlaceState.update { it.copy(public = newPublic) }
    }

    private suspend fun uploadCustomPhotoUri(uri: Uri) : Uri {
        if (uri.toString().isNotEmpty()) {
            val urlTask = storageService.uploadFile(uri = uri, "images")
            val url = urlTask.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.e("task not successful: ${task.exception}")
                }
            }.await()
            return url
        }
        return uri
    }
}
