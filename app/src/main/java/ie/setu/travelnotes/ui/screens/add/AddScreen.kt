package ie.setu.travelnotes.ui.screens.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.ui.components.add.AddPlaceButton
import ie.setu.travelnotes.ui.components.add.AddText
import ie.setu.travelnotes.ui.components.add.TextInput
import ie.setu.travelnotes.ui.components.add.TravelDatePicker
import java.time.LocalDate

@Composable
fun AddScreen(modifier: Modifier = Modifier,
              viewModel: AddViewModel = hiltViewModel()) {
    var placeName by remember { mutableStateOf("") }
    var placeDescription by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }


    Column(modifier = modifier.padding(
        start = 24.dp,
        end = 24.dp
    ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AddText()
        TravelDatePicker(
            value = LocalDate.now(),
            onDateSelected = { selectedDate = it }
        )
        TextInput(
//            value = placeName,
            label = "Place Name",
            onTextChange = { placeName = it },
            modifier = Modifier
        )
        TextInput(
//            value = placeDescription,
            label = "Place Description",
            onTextChange = { placeDescription = it }
        )
        AddPlaceButton(
            place = PlaceModel(
                name = placeName,
                description = placeDescription,
                date = selectedDate ?: LocalDate.now()
            ),
            onPlaceAdded = {
                placeName = ""
                placeDescription = ""
                selectedDate = LocalDate.now()
            }
        )

    }
}
