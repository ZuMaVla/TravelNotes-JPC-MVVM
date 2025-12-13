package ie.setu.travelnotes.ui.screens.add

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.data.room.repositories.PlaceRepository
import ie.setu.travelnotes.firebase.services.AuthService
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddViewModel  @Inject
constructor(private val placeRepository: PlaceRepository,
            private val AuthService: AuthService
) : ViewModel() {
    val userId = AuthService.userId!!
//    var selectedDate: Long? = null
    var isError = mutableStateOf(false)
    var errorBody = mutableStateOf(Exception())
    var isLoading = mutableStateOf(false)




    fun addPlace(place: PlaceModel) = viewModelScope.launch {
        try {
            isLoading.value = true
            placeRepository.insert(place, userId)
            isError.value = false
            isLoading.value = false
        } catch (e: Exception) {
            isError.value = true
            errorBody.value = e
            isLoading.value = false
            Timber.i("PVM Insert Message : ${errorBody.value.message}")
        }

    }


}
