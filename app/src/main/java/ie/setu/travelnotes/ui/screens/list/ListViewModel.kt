package ie.setu.travelnotes.ui.screens.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.data.room.repositories.PlaceRepository
import ie.setu.travelnotes.firebase.services.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListViewModel  @Inject
constructor(private val placeRepository: PlaceRepository,
            private val authService: AuthService) : ViewModel() {

    private val _places = MutableStateFlow<List<PlaceModel>>(emptyList())
    val uiPlaces: StateFlow<List<PlaceModel>> = _places.asStateFlow()
    var isError = mutableStateOf(false)
    var errorBody = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)

    val currentUser: FirebaseUser?
        get() = authService.currentUser
    var email = mutableStateOf("")

    init {
        getPlaces()
    }

    fun getPlaces() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                placeRepository.getAllByUser(authService.userId)
                    .collect { placesList ->
                        _places.value = placesList
                        isLoading.value = false
                        isError.value = false
                        Timber.i("LVM: List updated, size: ${placesList.size}")
                    }
           } catch (e: Exception) {
                isError.value = true
                errorBody.value = e
                isLoading.value = false
                Timber.i("LVM error message : ${errorBody.value.message}")
            }

        }
    }

    fun isLoggedIn() = authService.isUserAuthInFBase


}