package ie.setu.travelnotes.ui.components.general

import android.R
import android.R.attr.color
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ie.setu.travelnotes.ui.theme.TravelNotesTheme
import kotlin.toString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingPicker(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // 1. State to control whether the dropdown menu is expanded or not.
    var isExpanded by remember { mutableStateOf(false) }

    // The list of options that will be shown in the dropdown.
    val ratingOptions = (1..5).toList()

    // 2. Use ExposedDropdownMenuBox to manage the state and positioning.
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
        modifier = modifier
    ) {
        // 3. This is your custom "chip" UI. It now acts as the anchor for the menu.
        //    The .menuAnchor() modifier is crucial here.
        Surface(
            // We remove the onClick from here, as the Box handles it.
            color = Color.Black.copy(alpha = 0.5f),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .menuAnchor() // This tells the dropdown where to appear.
                .height(35.dp)
                .padding(end = 8.dp, bottom = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = "Rating",
                    tint = Color.Yellow,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = rating.toString(),
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        // 4. This is the actual dropdown menu that appears when expanded.
        //    It's the same as your RatingDropdownPicker's menu.
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            ratingOptions.forEach { ratingValue ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "$ratingValue ★",
//                            color = Color.Yellow,
                        )
                    },
                    onClick = {
                        onRatingChange(ratingValue) // Report the selected rating
                        isExpanded = false // Close the menu
                        },
                    modifier = Modifier.width(100.dp),
                )
            }
        }
    }
}

// A preview to test the combined component
@Preview(showBackground = true)
@Composable
private fun RatingPickerPreview() {
    TravelNotesTheme {
        var selectedRating by remember { mutableStateOf(3) }
        RatingPicker(
            rating = selectedRating,
            onRatingChange = { selectedRating = it }
        )
    }
}