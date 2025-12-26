package ie.setu.travelnotes.data.room.repositories

import ie.setu.travelnotes.data.UserModel
import ie.setu.travelnotes.data.room.UserDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject
constructor(private val userDAO: UserDAO) {
    fun getAll(): Flow<List<UserModel>> = userDAO.getAll()

    fun get(id: Long) = userDAO.get(id)

    suspend fun findByEmail(email: String): UserModel? {
        return userDAO.findByEmail(email)
    }

    suspend fun insert(user: UserModel)
    { userDAO.insert(user) }

    suspend fun update(user: UserModel)
    { userDAO.update(user) }

    suspend fun delete(user: UserModel)
    { userDAO.delete(user) }
}
