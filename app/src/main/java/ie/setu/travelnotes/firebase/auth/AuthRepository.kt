package ie.setu.travelnotes.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import ie.setu.travelnotes.firebase.services.AuthService
import ie.setu.travelnotes.firebase.services.FBaseSignInR
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository
@Inject constructor(private val fbAuth: FirebaseAuth)
    : AuthService {
        override var currentUser: FirebaseUser? = fbAuth.currentUser
        override var isUserAuthInFBase: Boolean = fbAuth.currentUser != null
        override var userId: String? = fbAuth.currentUser?.uid.orEmpty()

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

    override suspend fun signOut() {
        fbAuth.signOut()
        currentUser = null
        isUserAuthInFBase = false
        userId = null
    }

}