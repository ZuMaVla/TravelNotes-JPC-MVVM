package ie.setu.travelnotes.firebase.firestore

data class Rating(
    val value: Int,
    val userId: String
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
    var imageToDisplay: String = "",
    var lat: Double = 51.8985,
    var lng: Double = -8.4756,
    var rating: List<Rating> = listOf(),
    var public: Boolean = false
)
