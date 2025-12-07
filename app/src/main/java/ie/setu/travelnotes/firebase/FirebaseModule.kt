package ie.setu.travelnotes.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ie.setu.travelnotes.firebase.auth.AuthRepository
import ie.setu.travelnotes.firebase.services.AuthService

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideAuthRepository(auth: FirebaseAuth):
            AuthService = AuthRepository(fbAuth = auth)
}


