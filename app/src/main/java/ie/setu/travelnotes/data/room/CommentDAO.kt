package ie.setu.travelnotes.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ie.setu.travelnotes.data.CommentModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDAO {
    @Query("SELECT * FROM commentmodel")
    fun getAll(): Flow<List<CommentModel>>

    @Insert
    suspend fun insert(comment: CommentModel)

    @Update
    suspend fun update(comment: CommentModel)

    @Delete
    suspend fun delete(comment: CommentModel)
}