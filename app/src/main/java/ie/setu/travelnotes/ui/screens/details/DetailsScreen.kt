package ie.setu.travelnotes.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ie.setu.travelnotes.ui.components.details.DetailsHeader
import java.time.LocalDate


@Composable
fun DetailsScreen(modifier: Modifier = Modifier,
//                  placeId: String?,
                  viewModel: DetailsViewModel = hiltViewModel()) {
    val uiDetailsViewState = viewModel.uiDetailsViewState.collectAsState().value

    Column {
        DetailsHeader(
            modifier = modifier,
            uiDetailsViewState.placeName,
            uiDetailsViewState.placeDescription
        )
        Text(
            text = "Date: ${uiDetailsViewState.selectedDate}"
        )
    }
}