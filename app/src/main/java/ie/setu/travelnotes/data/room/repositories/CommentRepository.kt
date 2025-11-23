package ie.setu.travelnotes.data.room.repositories

import ie.setu.travelnotes.data.CommentModel
import ie.setu.travelnotes.data.room.CommentDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommentRepository @Inject
constructor(private val commentDAO: CommentDAO) {
    fun getAll(): Flow<List<CommentModel>> = commentDAO.getAll()

    suspend fun insert(comment: CommentModel)
    { commentDAO.insert(comment) }

    suspend fun update(comment: CommentModel)
    { commentDAO.update(comment) }

    suspend fun delete(comment: CommentModel)
    { commentDAO.delete(comment) }
}
