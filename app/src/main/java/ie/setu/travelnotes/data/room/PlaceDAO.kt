package ie.setu.travelnotes.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ie.setu.travelnotes.data.PlaceModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDAO {
    @Query("SELECT * FROM placemodel")
    fun getAll(): Flow<List<PlaceModel>>

    @Insert
    suspend fun insert(place: PlaceModel)

    @Update
    suspend fun update(place: PlaceModel)

    @Delete
    suspend fun delete(place: PlaceModel)
}