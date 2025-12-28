package ie.setu.travelnotes.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ie.setu.travelnotes.ui.components.details.DetailsHeader
import ie.setu.travelnotes.ui.components.general.ImageGallery


@Composable
fun DetailsScreen(modifier: Modifier = Modifier,
                  viewModel: DetailsViewModel = hiltViewModel()) {
    val uiDetailsViewState = viewModel.uiDetailsViewState.collectAsState().value


    if (!uiDetailsViewState.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                ImageGallery(
                    uri = uiDetailsViewState.imageToDisplay,
                    rating = viewModel.getCurrentUserRating(),
                    onRatingChange = { viewModel.onRatingChange(vote = it) }
                )
            }
            Column {
                Column {
                    DetailsHeader(
                        modifier = modifier,
                        uiDetailsViewState.placeName,
                        uiDetailsViewState.placeDescription
                    )
                }
            }
        }
    } else {
        CircularProgressIndicator()
    }
}
