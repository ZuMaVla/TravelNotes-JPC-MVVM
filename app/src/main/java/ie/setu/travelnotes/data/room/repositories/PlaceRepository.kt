package ie.setu.travelnotes.data.room.repositories

import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.data.room.PlaceDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

class PlaceRepository @Inject
constructor(private val placeDAO: PlaceDAO) {

    fun getAll(): Flow<List<PlaceModel>> = placeDAO.getAll()

    fun getAllByUser(userId: String): Flow<List<PlaceModel>> = placeDAO.getAllByUser(userId)

    fun getPlaceById(placeId: String): Flow<PlaceModel> = placeDAO.getPlaceById(placeId)


    suspend fun insert(place: PlaceModel, userId: String) {
        place.userId = userId
        placeDAO.insert(place)
    }

    suspend fun update(place: PlaceModel, userId: String) {
        if (place.userId == userId) { placeDAO.update(place) }
        else { Timber.e("User ID mismatch") }
    }

    suspend fun delete(place: PlaceModel, userId: String) {
        if (place.userId == userId) { placeDAO.delete(place) }
    }
}
