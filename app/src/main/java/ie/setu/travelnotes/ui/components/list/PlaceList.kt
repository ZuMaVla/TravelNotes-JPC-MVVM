package ie.setu.travelnotes.ui.components.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
//import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.firebase.firestore.PlaceModel
import ie.setu.travelnotes.firebase.firestore.toMillis
import ie.setu.travelnotes.ui.theme.TravelNotesTheme

@Composable
internal fun PlaceList(
    modifier: Modifier = Modifier,
    selectedPlace: PlaceModel?,
    places: List<PlaceModel>,
    onPlaceClick: (PlaceModel) -> Unit,
    onPlaceLongClick: (PlaceModel) -> Unit,
    onRefreshList: () -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = places,
            key = { place -> place.id }
        ) { place ->
            PlaceCard(
                selectedPlace = selectedPlace,
                place = place,
                onPlaceClick = { onPlaceClick(place) },
                onPlaceLongClick = { onPlaceLongClick(place) },
                onRefreshList = onRefreshList
            )
        }
    }
}

@Preview(showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE)
@Composable
fun PlaceListPreview() {
    TravelNotesTheme {
        PlaceList(
            selectedPlace = null,
            places = listOf(
                PlaceModel(
                    name = "Test Place1",
                    description = "Test Description1",
                    dateMillis = java.time.LocalDate.now().toMillis(),
                    id = "1"
                ),
                PlaceModel(
                    name = "Test Place2",
                    description = "Test Description2",
                    dateMillis = java.time.LocalDate.now().toMillis(),
                    id = "2"
                )
                ),
            onPlaceClick = {},
            onPlaceLongClick = {},
            onRefreshList = {}
        )
    }


}