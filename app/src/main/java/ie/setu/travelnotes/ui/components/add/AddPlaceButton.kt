package ie.setu.travelnotes.ui.components.add

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.ui.screens.add.AddViewModel
import ie.setu.travelnotes.ui.screens.list.ListViewModel

@Composable
fun AddPlaceButton(
    modifier: Modifier = Modifier,
    isEdit: Boolean = false,
    isLoading: Boolean,
    addUpdatePlace: () -> Unit
) {
    val context = LocalContext.current

    if (isLoading) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Text("Saving Place...")
        }
    } else {
        Button(
            onClick = {
                addUpdatePlace()
            },
            modifier = modifier
        ) { Text( text = if (!isEdit) {"Add Place"} else {"Update Place"})}
    }
}



