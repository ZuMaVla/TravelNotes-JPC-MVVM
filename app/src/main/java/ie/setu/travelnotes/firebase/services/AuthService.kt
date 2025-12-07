package ie.setu.travelnotes.firebase.services

import com.google.firebase.auth.FirebaseUser
import ie.setu.travelnotes.firebase.auth.Response

typealias FBaseSignInR = Response<FirebaseUser>

interface AuthService {
    var currentUser: FirebaseUser?
    var isUserAuthInFBase: Boolean
    var userId: String?

    suspend fun signInUp(email: String, password: String): FBaseSignInR
    suspend fun signOut()
}