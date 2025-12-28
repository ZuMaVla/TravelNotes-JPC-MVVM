package ie.setu.travelnotes.firebase.firestore

import ie.setu.travelnotes.R
import ie.setu.travelnotes.ui.screens.IMAGE_PLACEHOLDER_URI

data class Rating(
    val value: Int = 0,
    val userId: String = ""
)

data class PlaceModel(

    // Firestore document ID
    var id: String = "",

    // Owner
    var userId: String = "",

    var name: String = "",
    var description: String = "",

    // Store as epoch millis
    var dateMillis: Long = System.currentTimeMillis(),
    // Store URIs of image gallery
    var imageUris: List<String> = listOf(),
    // Store image URI for display
    var imageToDisplay: String = IMAGE_PLACEHOLDER_URI,
    var lat: Double = 51.8985,
    var lng: Double = -8.4756,
    var rating: List<Rating> = listOf(),
    var public: Boolean = false
)
