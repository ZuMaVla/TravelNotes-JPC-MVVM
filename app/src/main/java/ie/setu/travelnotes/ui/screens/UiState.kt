package ie.setu.travelnotes.ui.screens

import ie.setu.travelnotes.firebase.firestore.Rating
import ie.setu.travelnotes.firebase.firestore.toMillis
import java.time.LocalDate

data class UiPlaceState(
    val placeName: String = "",
    val placeDescription: String = "",
    val selectedDate: Long = LocalDate.now().toMillis(),
    val id: String = "",
    val imageUris: List<String> = listOf(),
    val imageToDisplay: String = "",
    val rating: List<Rating> = listOf(),
    val avgRating: Double = 0.0,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null,
    val isSaved: Boolean = false,
    val isEdit: Boolean = false
)