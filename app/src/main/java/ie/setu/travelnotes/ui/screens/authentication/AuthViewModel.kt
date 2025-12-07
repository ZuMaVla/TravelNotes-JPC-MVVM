package ie.setu.travelnotes.ui.screens.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.travelnotes.data.UserModel
import ie.setu.travelnotes.data.room.repositories.UserRepository
import ie.setu.travelnotes.firebase.auth.Response
import ie.setu.travelnotes.firebase.services.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject

constructor(private val userRepository: UserRepository,
            private val authService: AuthService) : ViewModel() {
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

    fun signOut() {
        viewModelScope.launch {
            _loginState.value = Response.Success(false)
            email = ""
            password = ""
            authService.signOut()
        }
    }

}
