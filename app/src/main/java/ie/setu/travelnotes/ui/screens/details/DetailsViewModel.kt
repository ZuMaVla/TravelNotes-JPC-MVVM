package ie.setu.travelnotes.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
//import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.data.room.repositories.PlaceRepository
import ie.setu.travelnotes.firebase.firestore.FirestorePlaceRepository
import ie.setu.travelnotes.firebase.firestore.PlaceModel
import ie.setu.travelnotes.firebase.firestore.localDate
import ie.setu.travelnotes.navigation.Details
import ie.setu.travelnotes.ui.screens.add.UiAddEditPlaceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

data class UiDetailsViewState(
    val placeName: String = "",
    val placeDescription: String = "",
    val selectedDate: LocalDate = LocalDate.now(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class DetailsViewModel  @Inject
constructor(private val placeRepository: FirestorePlaceRepository,
            private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private var _uiDetailsViewState = MutableStateFlow(UiDetailsViewState())
    val uiDetailsViewState: StateFlow<UiDetailsViewState> = _uiDetailsViewState.asStateFlow()


    init {
        val placeId: String? = savedStateHandle[Details.placeId]
        getPlace(placeId)
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
                        selectedDate = placeToDisplay!!.dateMillis.localDate(),
                        isLoading = false,
                        error = null,
                    )
                }

                Timber.i("DVM Message : PlaceID : ${placeToDisplay!!.id}")
            }
        }
    }

}


