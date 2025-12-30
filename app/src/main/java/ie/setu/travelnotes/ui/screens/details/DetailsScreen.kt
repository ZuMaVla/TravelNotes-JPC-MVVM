package ie.setu.travelnotes.ui.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ie.setu.travelnotes.ui.components.details.DetailsHeader
import ie.setu.travelnotes.ui.components.general.ImageGallery


@Composable
fun DetailsScreen(modifier: Modifier = Modifier,
                  viewModel: DetailsViewModel = hiltViewModel()) {
    val uiDetailsViewState = viewModel.uiDetailsViewState.collectAsState().value

    when {
        uiDetailsViewState.isLoading -> {
            CircularProgressIndicator(modifier = modifier.fillMaxSize())
        }

        uiDetailsViewState.isError -> {
            Text(
                text = uiDetailsViewState.error ?: "An unknown error occurred.",
                modifier = modifier.padding(16.dp)
            )
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = modifier
                        .height(250.dp)
                ) {
                    ImageGallery(
                        uri = uiDetailsViewState.imageToDisplay,
                        rating = viewModel.getCurrentUserRating(),
                        onRatingChange = { viewModel.onRatingChange(vote = it) },
                        onClick = { viewModel.onGalleryClick() }
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
        }

    }

}
