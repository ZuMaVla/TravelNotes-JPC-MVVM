package ie.setu.travelnotes.ui.components.list

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ie.setu.travelnotes.firebase.firestore.PlaceModel
import ie.setu.travelnotes.firebase.firestore.localDate
import ie.setu.travelnotes.firebase.firestore.toMillis
import ie.setu.travelnotes.ui.theme.TravelNotesTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ie.setu.travelnotes.ui.screens.getAvgRating


@Composable
fun PlaceCard(
    modifier: Modifier = Modifier,
    selectedPlace: PlaceModel?,
    place: PlaceModel,
    imageUri: String,
    onPlaceClick: () -> Unit,
    onPlaceLongClick: (place: PlaceModel) -> Unit,
    onRefreshList: () -> Unit,
    currentUserId: String = ""
) {
    val context = LocalContext.current

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
                onLongClick = { if (place.userId != currentUserId) {
                    Toast.makeText(
                        context,
                        "You cannot edit or delete places created by other users",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    onPlaceLongClick(place)
                } }
            )
    ) {
        PlaceCardContent(
            place = place,
            imageUri = imageUri,
            onRefreshList = onRefreshList,
            modifier = modifier,
        )
    }
}

@Composable
fun PlaceCardContent(place: PlaceModel,
                     imageUri: String,
                     onRefreshList: () -> Unit,
                     modifier: Modifier = Modifier
) {

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        ) {
            Text(
                text = place.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = place.description,
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = place.localDate().toString(),
                style = MaterialTheme.typography.bodySmall
            )


        }
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(90.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri?.toUri())
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()

            )
            Surface(
                color = Color.Black.copy(alpha = 0.5f),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(2.dp)
                    .height(25.dp)
                    .width(45.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.StarRate,
                        contentDescription = null,
                        tint = Color.Yellow,
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = getAvgRating(place.rating).toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }
        }
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
            onRefreshList = {},
            imageUri = ""
        )
    }
}

