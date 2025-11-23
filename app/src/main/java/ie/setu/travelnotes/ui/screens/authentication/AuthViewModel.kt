package ie.setu.travelnotes.ui.screens.authentication

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.travelnotes.data.room.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel  @Inject
constructor(private val userRepository: UserRepository) : ViewModel() {
    init { }

}
