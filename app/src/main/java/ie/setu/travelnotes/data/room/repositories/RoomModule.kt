package ie.setu.travelnotes.data.room.repositories

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ie.setu.travelnotes.data.room.AppDB
import ie.setu.travelnotes.data.room.CommentDAO
import ie.setu.travelnotes.data.room.PlaceDAO
import ie.setu.travelnotes.data.room.UserDAO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context):
            AppDB =
        Room.databaseBuilder(context, AppDB::class.java,"travelnotes.db")
                .fallbackToDestructiveMigration(false)
                .build()

    @Provides
    fun provideUserDAO(appDB: AppDB):
            UserDAO = appDB.getUserDAO()

    @Provides
    fun provideUserRepository(userDAO: UserDAO):
            UserRepository = UserRepository(userDAO)

    @Provides
    fun providePlaceDAO(appDB: AppDB):
            PlaceDAO = appDB.getPlaceDAO()

    @Provides
    fun providePlaceRepository(placeDAO: PlaceDAO):
            PlaceRepository = PlaceRepository(placeDAO)

    @Provides
    fun provideCommentDAO(appDB: AppDB):
            CommentDAO = appDB.getCommentDAO()

    @Provides
    fun provideCommentRepository(commentDAO: CommentDAO):
            CommentRepository = CommentRepository(commentDAO)
}