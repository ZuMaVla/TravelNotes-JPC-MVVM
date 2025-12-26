package ie.setu.travelnotes.firebase.services

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import ie.setu.travelnotes.firebase.auth.Response
import kotlinx.coroutines.flow.StateFlow

typealias FBaseSignInR = Response<FirebaseUser>
typealias SignInWithGoogleR = Response<Boolean>

interface AuthService {
    val currentUser: FirebaseUser?
    val isUserAuthInFBase: Boolean
    val userId: String

    val authStateFlow: StateFlow<FirebaseUser?>

    suspend fun signInUp(email: String, password: String): FBaseSignInR
    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleR
    suspend fun authenticateGoogleUser(googleIdToken: String): FBaseSignInR
    suspend fun signOut()
}