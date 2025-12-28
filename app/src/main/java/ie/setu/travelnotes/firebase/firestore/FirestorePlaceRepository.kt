package ie.setu.travelnotes.firebase.firestore

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import ie.setu.travelnotes.firebase.firestore.PlaceModel
import ie.setu.travelnotes.firebase.services.AuthService
import ie.setu.travelnotes.firebase.services.FirestoreService
import ie.setu.travelnotes.firebase.services.Place
import ie.setu.travelnotes.firebase.services.Places
import ie.setu.travelnotes.firebase.services.StorageService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class FirestorePlaceRepository @Inject
constructor(private val auth: AuthService,
            private val firestore: FirebaseFirestore,
) : FirestoreService {
    private val placesCollection = firestore.collection("places")

    // Get all places
    override suspend fun getAll(): Places {
        return placesCollection.dataObjects()
    }

    // Get all places for a specific user
    override suspend fun getAllByUser(userId: String): Places {
        val snapshot = placesCollection
            .where(
                Filter.or(
                    Filter.equalTo("userId", userId),
                    Filter.equalTo("public", true)
                )
            )
        return snapshot.dataObjects()
    }

    // Get single place by ID
    override suspend fun getPlaceById(placeId: String): Place? {
        val snapshot = placesCollection.document(placeId).get().await()
        return snapshot.toObject()
    }

    override suspend fun insert(place: Place, userId: String) {
        try {
            val documentRef = placesCollection.document()
            val ownedPlace = place.copy(id = documentRef.id, userId = userId)
            documentRef.set(ownedPlace).await()
            Timber.i("Inserted place with ID: ${ownedPlace.id}")
        } catch (e: Exception) {
            Timber.e(e, "Error inserting place")
        }
    }

    override suspend fun update(place: Place, userId: String) {
        try {
            val storedPlace = getPlaceById(place.id)
            if (storedPlace?.userId == userId) {
                placesCollection.document(place.id).set(place).await()
                Timber.i("Updated place with ID: ${place.id}")
            } else {
                Timber.e("User ID mismatch or place not found: Place's user ID: ${storedPlace?.userId}, User ID: $userId")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error updating place")
        }
    }

    override suspend fun delete(place: Place, userId: String) {
        try {
            val storedPlace = getPlaceById(place.id)
            if (storedPlace?.userId == userId) {
                placesCollection.document(place.id).delete().await()
                Timber.i("Deleted place with ID: ${storedPlace.id}")
            } else {
                Timber.e("User ID mismatch: Place's user ID: ${storedPlace?.userId}, User ID: $userId")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error deleting place")
        }
    }
    override suspend fun updateRating(placeId: String?, rating: List<Rating>) {
        try {
            if (placeId != null) {
                placesCollection.document(placeId).update("rating", rating).await()
                Timber.i("Updated rating for place with ID: $placeId")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error updating rating")
        }
    }
}
