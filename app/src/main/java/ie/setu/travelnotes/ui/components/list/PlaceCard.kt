package ie.setu.travelnotes.ui.components.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.ui.theme.TravelNotesTheme

@Composable
fun PlaceCard(
    modifier: Modifier = Modifier,
    place: PlaceModel,
    onPlaceClick: () -> Unit,
    onRefreshList: () -> Unit
) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .clickable { onPlaceClick() }
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
            text = place.date.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceCardPreview() {
    TravelNotesTheme {
        PlaceCard(
            place = PlaceModel(
                name = "Test Place",
                description = "Test Description",
                date = java.time.LocalDate.now()
            ),
            onPlaceClick = {},
            onRefreshList = {}
        )
    }
}

