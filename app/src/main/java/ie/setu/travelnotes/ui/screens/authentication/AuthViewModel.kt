package ie.setu.travelnotes.ui.screens.authentication

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.travelnotes.data.UserModel
import ie.setu.travelnotes.data.room.repositories.UserRepository
import ie.setu.travelnotes.firebase.auth.Response
import ie.setu.travelnotes.firebase.services.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject

constructor(private val userRepository: UserRepository,
            private val authService: AuthService,
            private val credentialManager: CredentialManager,
            private val credentialRequest: GetCredentialRequest
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    fun insert(user: UserModel) = viewModelScope.launch {
        userRepository.insert(user)
    }
    private val _loginState = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val loginState: StateFlow<Response<Boolean>> = _loginState

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        if (authService.isUserAuthInFBase) {
            _loginState.value = Response.Success(true)
        }
    }


    fun login() = viewModelScope.launch {
        _loginState.value = Response.Loading
        val result = authService.signInUp(email, password)
        when (result) {
            is Response.Success -> {
                _loginState.value = Response.Success(true)
                _error.value = null
            }

            is Response.Error -> {
                _loginState.value = Response.Error(result.exception)
            }

            else -> {}
        }
    }

    fun signInWithGoogle(credentialsContext : Context) {
        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = credentialRequest,
                    context = credentialsContext,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                // handleFailure(e)
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        val googleIdToken = googleIdTokenCredential.idToken
                        loginGoogleUser(googleIdToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Timber.tag("TAG").e(e, "Received an invalid google id token response")
                    }
                }
            }
        }
    }

    private fun loginGoogleUser(googleIdToken: String) = viewModelScope.launch {

        _loginState.value = Response.Loading
        val result = authService.authenticateGoogleUser(googleIdToken)
        when (result) {
            is Response.Success -> {
                _loginState.value = Response.Success(true)
                _error.value = null
            }

            is Response.Error -> {
                _loginState.value = Response.Error(result.exception)
            }

            else -> {}
        }
    }


    fun signOut() {
        viewModelScope.launch {
            _loginState.value = Response.Success(false)
            email = ""
            password = ""
            authService.signOut()
        }
    }

}
