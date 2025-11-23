package ie.setu.travelnotes.data.room.repositories

import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.data.room.PlaceDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaceRepository @Inject
constructor(private val placeDAO: PlaceDAO) {
    fun getAll(): Flow<List<PlaceModel>> = placeDAO.getAll()

    suspend fun insert(place: PlaceModel)
    { placeDAO.insert(place) }

    suspend fun update(place: PlaceModel)
    { placeDAO.update(place) }

    suspend fun delete(place: PlaceModel)
    { placeDAO.delete(place) }
}
