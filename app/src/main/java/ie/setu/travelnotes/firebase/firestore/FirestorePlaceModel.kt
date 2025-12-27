package ie.setu.travelnotes.firebase.firestore



data class PlaceModel(

    // Firestore document ID
    var id: String = "",

    // Owner
    var userId: String = "",

    var name: String = "",
    var description: String = "",

    // Store as epoch millis
    var dateMillis: Long = System.currentTimeMillis(),

    // Store as URL or empty string
    var imageUrl: String = "",

    var lat: Double = 51.8985,
    var lng: Double = -8.4756,
    var rating: Double = 0.0,
    var public: Boolean = false
)
