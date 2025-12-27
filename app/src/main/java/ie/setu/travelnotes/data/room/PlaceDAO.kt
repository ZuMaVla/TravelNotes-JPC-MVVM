package ie.setu.travelnotes.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ie.setu.travelnotes.data.RoomPlaceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDAO {
    @Query("SELECT * FROM roomplacemodel")
    fun getAll(): Flow<List<RoomPlaceModel>>

    @Query("SELECT * FROM roomplacemodel WHERE (userId = :userId OR public = true)" )
    fun getAllByUser(userId: String): Flow<List<RoomPlaceModel>>

    @Query("SELECT * FROM roomplacemodel WHERE (id = :placeId)" )
    fun getPlaceById(placeId: String): Flow<RoomPlaceModel>

    @Insert
    suspend fun insert(place: RoomPlaceModel)

    @Update
    suspend fun update(place: RoomPlaceModel)

    @Delete
    suspend fun delete(place: RoomPlaceModel)
}