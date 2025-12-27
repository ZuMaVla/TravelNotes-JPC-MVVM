package ie.setu.travelnotes.data.room.repositories

import ie.setu.travelnotes.data.RoomPlaceModel
import ie.setu.travelnotes.data.room.PlaceDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

class PlaceRepository @Inject
constructor(private val placeDAO: PlaceDAO) {

    fun getAll(): Flow<List<RoomPlaceModel>> = placeDAO.getAll()

    fun getAllByUser(userId: String): Flow<List<RoomPlaceModel>> = placeDAO.getAllByUser(userId)

    fun getPlaceById(placeId: String): Flow<RoomPlaceModel> = placeDAO.getPlaceById(placeId)


    suspend fun insert(place: RoomPlaceModel, userId: String) {
        place.userId = userId
        placeDAO.insert(place)
    }

    suspend fun update(place: RoomPlaceModel, userId: String) {
        if (place.userId == userId) { placeDAO.update(place) }
        else { Timber.e("User ID mismatch: Place's user ID: ${place.userId}, User ID: $userId") }
    }

    suspend fun delete(place: RoomPlaceModel, userId: String) {
        if (place.userId == userId) { placeDAO.delete(place) }
    }
}
