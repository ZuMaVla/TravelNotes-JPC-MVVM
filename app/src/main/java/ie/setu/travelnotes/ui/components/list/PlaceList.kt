package ie.setu.travelnotes.ui.components.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.ui.theme.TravelNotesTheme

@Composable
internal fun PlaceList(
    modifier: Modifier = Modifier,
    places: List<PlaceModel>,
    onPlaceClick: (PlaceModel) -> Unit,
    onRefreshList: () -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(
            items = places,
            key = { place -> place.id }
        ) { place ->
            PlaceCard(
                place = place,
                onPlaceClick = { onPlaceClick(place) },
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
            places = listOf(
                PlaceModel(
                    name = "Test Place1",
                    description = "Test Description1",
                    date = java.time.LocalDate.now(),
                    id = 1
                ),
                PlaceModel(
                    name = "Test Place2",
                    description = "Test Description2",
                    date = java.time.LocalDate.now(),
                    id = 2
                )
                ),
            onPlaceClick = {},
            onRefreshList = {}
        )
    }


}