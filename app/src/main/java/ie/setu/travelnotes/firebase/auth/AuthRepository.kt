package ie.setu.travelnotes.firebase.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import ie.setu.travelnotes.firebase.services.AuthService
import ie.setu.travelnotes.firebase.services.FBaseSignInR
import ie.setu.travelnotes.firebase.services.SignInWithGoogleR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository
@Inject constructor(private val fbAuth: FirebaseAuth)
    : AuthService {
        private val _authStateFlow = MutableStateFlow<FirebaseUser?>(fbAuth.currentUser)
        override val authStateFlow: StateFlow<FirebaseUser?> = _authStateFlow
        override val currentUser: FirebaseUser?
            get() = authStateFlow.value
        override val isUserAuthInFBase: Boolean
            get() = authStateFlow.value != null
        override val userId: String
            get() = authStateFlow.value?.uid ?: "guest"

    init {
        fbAuth.addAuthStateListener { auth ->  _authStateFlow.value = auth.currentUser }
    }

    override suspend fun signInUp(email: String, password: String): FBaseSignInR {
        try {
            val result = fbAuth.signInWithEmailAndPassword(email, password).await()
            return Response.Success(result.user!!)
        } catch (e: Exception) {
            if ((e is FirebaseAuthInvalidCredentialsException)) {
                    try {
                        val result2 = fbAuth.createUserWithEmailAndPassword(email, password).await()
                        return Response.Success(result2.user!!)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        return Response.Error(e)
                    }

                } else {
                e.printStackTrace()
                return Response.Error(e)
            }
        }

    }

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleR {
        return try {
            val authResult = fbAuth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                //   addUserToFirestore()
            }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    override suspend fun authenticateGoogleUser(googleIdToken: String) : FBaseSignInR {
        return try {
            val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val result = fbAuth.signInWithCredential(firebaseCredential).await()
            Response.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Error(e)
        }
    }

    override suspend fun signOut() {
        fbAuth.signOut()
    }

}