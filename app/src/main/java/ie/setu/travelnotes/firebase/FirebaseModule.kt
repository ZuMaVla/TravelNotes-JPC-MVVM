package ie.setu.travelnotes.firebase

import android.app.Application
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ie.setu.travelnotes.R
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

    @Provides
    fun provideCredentialManager(
        @ApplicationContext
        context: Context
    ) = CredentialManager.create(context)

    @Provides
    fun provideGoogleIdOptions(
        app: Application
    ) = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(app.getString(R.string.web_client_id))
        .build()

    @Provides
    fun getCredentialRequest(
        googleIdOption: GetGoogleIdOption
    ) = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

}


