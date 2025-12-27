package ie.setu.travelnotes.firebase.services

import ie.setu.travelnotes.firebase.firestore.PlaceModel
import kotlinx.coroutines.flow.Flow

typealias Place = PlaceModel
typealias Places = Flow<List<Place>>

interface FirestoreService {

    suspend fun getAll() : Places
    suspend fun getAllByUser(userId: String) : Places
    suspend fun getPlaceById(placeId: String) : Place?
    suspend fun insert(place: Place, userId: String)
    suspend fun update(place: Place, userId: String)
    suspend fun delete(place: Place, userId: String)
}