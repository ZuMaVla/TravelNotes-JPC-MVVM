package ie.setu.travelnotes.ui.components.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.travelnotes.firebase.firestore.PlaceModel
import ie.setu.travelnotes.firebase.firestore.localDate
import ie.setu.travelnotes.firebase.firestore.toMillis
import ie.setu.travelnotes.ui.theme.TravelNotesTheme

@Composable
fun PlaceCard(
    modifier: Modifier = Modifier,
    selectedPlace: PlaceModel?,
    place: PlaceModel,
    onPlaceClick: () -> Unit,
    onPlaceLongClick: () -> Unit,
    onRefreshList: () -> Unit
) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = if (selectedPlace?.id == place.id) {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .combinedClickable(
                onClick = { onPlaceClick() },
                onLongClick = { onPlaceLongClick() }
            )
    ) {
        PlaceCardContent(
            place = place,
            onRefreshList = onRefreshList,
            modifier = modifier
        )
    }
}

@Composable
fun PlaceCardContent(place: PlaceModel,
                     onRefreshList: () -> Unit,
                     modifier: Modifier = Modifier) {
    Column(
        modifier
            .padding(14.dp)
    ) {
        Text(
            text = place.name,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = place.description,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = place.localDate().toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceCardPreview() {
    TravelNotesTheme {
        PlaceCard(
            selectedPlace = null,
            place = PlaceModel(
                name = "Test Place",
                description = "Test Description",
                dateMillis = java.time.LocalDate.now().toMillis(),
            ),
            onPlaceClick = {},
            onPlaceLongClick = {},
            onRefreshList = {}
        )
    }
}

