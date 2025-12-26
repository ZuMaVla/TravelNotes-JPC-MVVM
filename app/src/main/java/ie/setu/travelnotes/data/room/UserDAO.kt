package ie.setu.travelnotes.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ie.setu.travelnotes.data.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Query("SELECT * FROM usermodel")
    fun getAll(): Flow<List<UserModel>>

    @Query("SELECT * FROM usermodel WHERE id=:id")
    fun get(id: Long): Flow<UserModel>

    @Query("SELECT * FROM usermodel WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): UserModel?

    @Query("SELECT * FROM usermodel WHERE email = :email AND password = :password LIMIT 1")
    suspend fun findByEmailAndPassword(email: String, password: String): UserModel?

    @Insert
    suspend fun insert(user: UserModel)

    @Update
    suspend fun update(user: UserModel)

    @Delete
    suspend fun delete(user: UserModel)
}