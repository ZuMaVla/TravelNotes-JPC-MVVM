package ie.setu.travelnotes.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ie.setu.travelnotes.data.CommentModel
import ie.setu.travelnotes.data.RoomPlaceModel
import ie.setu.travelnotes.data.UserModel

@Database(entities = [UserModel::class, RoomPlaceModel::class, CommentModel::class], version = 5)
@TypeConverters(Converters::class)
abstract class AppDB : RoomDatabase() {
    abstract fun getUserDAO(): UserDAO
    abstract fun getPlaceDAO(): PlaceDAO
    abstract fun getCommentDAO(): CommentDAO
}
