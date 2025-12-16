package ie.setu.travelnotes.ui.screens.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
            value = selectedDate ?: LocalDate.now(),
            onDateSelected = { selectedDate = it }
        )
        TextInput(
            value = placeName,
            onTextChange = { placeName = it },
            label = "Place Name",
            modifier = modifier
        )
        TextInput(
            value = placeDescription,
            onTextChange = { placeDescription = it },
            label = "Place Description",
            modifier = modifier
        )
        AddPlaceButton(
            place = PlaceModel(
                name = placeName,
                description = placeDescription,
                date = selectedDate ?: LocalDate.now(),
                userId = viewModel.userId
            ),
            addViewModel = viewModel,
            onPlaceAdded = {
                placeName = ""
                placeDescription = ""
                selectedDate = LocalDate.now()
            }
        )

    }
}
