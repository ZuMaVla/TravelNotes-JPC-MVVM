package ie.setu.travelnotes.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
//import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.firebase.firestore.FirestorePlaceRepository
import ie.setu.travelnotes.firebase.firestore.PlaceModel
import ie.setu.travelnotes.firebase.firestore.localDate
import ie.setu.travelnotes.firebase.services.AuthService
import ie.setu.travelnotes.navigation.Details
import ie.setu.travelnotes.ui.screens.UiPlaceState
import ie.setu.travelnotes.ui.screens.getAvgRating
import ie.setu.travelnotes.ui.screens.getIndividualRating
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel  @Inject
constructor(
    private val placeRepository: FirestorePlaceRepository,
    private val savedStateHandle: SavedStateHandle,
    private val authService: AuthService,
) : ViewModel() {
    private var _uiDetailsViewState = MutableStateFlow(UiPlaceState())
    val uiDetailsViewState: StateFlow<UiPlaceState> = _uiDetailsViewState.asStateFlow()


    init {
        val placeId: String? = savedStateHandle[Details.placeId]
        getPlace(placeId)
    }

    fun getCurrentUserRating(): Int {
        return getIndividualRating(uiDetailsViewState.value.rating, authService.userId)
    }
    fun getPlace(placeId: String?) {
        var placeToDisplay: PlaceModel? = PlaceModel()
        viewModelScope.launch {
            _uiDetailsViewState.update { it.copy(isLoading = true) }
            try {
                placeToDisplay = if (!placeId.isNullOrEmpty()) {
                    placeRepository.getPlaceById(placeId)
                } else {
                    null
                }
            } catch (e: Exception) {
                placeToDisplay = PlaceModel()
            }
            finally {
                _uiDetailsViewState.update {
                    it.copy(
                        placeName = placeToDisplay!!.name,
                        placeDescription = placeToDisplay!!.description,
                        selectedDate = placeToDisplay!!.dateMillis,
                        rating = placeToDisplay!!.rating,
                        avgRating = getAvgRating(placeToDisplay!!.rating),
                        id = placeToDisplay!!.id,
                        imageUris = placeToDisplay!!.imageUris,
                        imageToDisplay = placeToDisplay!!.imageToDisplay,
                        isLoading = false,
                        error = null,
                    )
                }

                Timber.i("DVM Message : PlaceID : ${placeToDisplay!!.id}")
            }
        }
    }

}


