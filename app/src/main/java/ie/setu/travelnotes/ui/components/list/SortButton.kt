package ie.setu.travelnotes.ui.components.list

import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.travelnotes.ui.theme.TravelNotesTheme

@Composable
fun SortFAB(
    onSortOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isMenuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.wrapContentSize(Alignment.TopStart)
    ) {
        FloatingActionButton(onClick = { isMenuExpanded = true }) {
            Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Sort Places")
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Sort by Name") },
                onClick = {
                    onSortOptionSelected("NAME")
                    isMenuExpanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Sort by Date") },
                onClick = {
                    onSortOptionSelected("DATE")
                    isMenuExpanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Sort by Rating") },
                onClick = {
                    onSortOptionSelected("RATING")
                    isMenuExpanded = false
                }
            )
        }
    }
}

@Preview
@Composable
fun SortFabPreview() {
    TravelNotesTheme {
        SortFAB(onSortOptionSelected = {})
    }
}
