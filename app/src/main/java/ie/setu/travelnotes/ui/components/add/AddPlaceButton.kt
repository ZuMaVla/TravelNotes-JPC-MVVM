package ie.setu.travelnotes.ui.components.add

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.ui.screens.add.AddViewModel
import ie.setu.travelnotes.ui.screens.list.ListViewModel

@Composable
fun AddPlaceButton(
    modifier: Modifier = Modifier,
    place: PlaceModel,
    addViewModel: AddViewModel = hiltViewModel(),
    listViewModel: ListViewModel = hiltViewModel(),
    onPlaceAdded: () -> Unit
) {
    val isError = addViewModel.isError.value
    val errorBody = addViewModel.errorBody.value
    val isLoading = addViewModel.isLoading.value
    val context = LocalContext.current

    if(isLoading) {
        CircularProgressIndicator()
        Text("Adding New Place...")
    }
    Button(
        onClick = {
            addViewModel.addPlace(place)
        }

    ) {
        Text(text = "Add Place")

    }


}
