package ie.setu.travelnotes.ui.screens.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.travelnotes.data.room.repositories.PlaceRepository
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel  @Inject
constructor(private val placeRepository: PlaceRepository) : ViewModel() {
    init { }
}


