package ie.setu.travelnotes.ui.screens.add

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ie.setu.travelnotes.data.PlaceModel
import ie.setu.travelnotes.ui.components.add.AddPlaceButton
import ie.setu.travelnotes.ui.components.add.AddText
import ie.setu.travelnotes.ui.components.add.EditText
import ie.setu.travelnotes.ui.components.add.TextInput
import ie.setu.travelnotes.ui.components.add.TravelDatePicker
import timber.log.Timber

@Composable
fun AddScreen(modifier: Modifier = Modifier,
              isEdit: Boolean,
              viewModel: AddViewModel = hiltViewModel(),
              onPlaceUpdateSuccess: () -> Unit,
              navigateUp: () -> Unit
) {
    Timber.i("isEditing: $isEdit")
    val uiAddEditPlaceState = viewModel.uiAddEditPlaceState.collectAsState().value
    val context = LocalContext.current
    LaunchedEffect(uiAddEditPlaceState.isSaved) {
        if (uiAddEditPlaceState.isSaved) {
            viewModel.onPlaceAdded()
            viewModel.onNavigateAway()
            Toast.makeText(context, "Place Saved", Toast.LENGTH_LONG).show()
            onPlaceUpdateSuccess()
            navigateUp()
        }
    }

    Column(
        modifier = modifier.padding(
            start = 24.dp,
            end = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (!isEdit) AddText() else EditText()

        TravelDatePicker(
            value = uiAddEditPlaceState.selectedDate,
            onDateSelected = { viewModel.onDateChange(it) }
        )
        TextInput(
            value = uiAddEditPlaceState.placeName,
            onTextChange = { viewModel.onNameChange(it)},
            label = "Place Name",
            modifier = modifier
        )
        TextInput(
            value = uiAddEditPlaceState.placeDescription,
            onTextChange = { viewModel.onDescriptionChange(it) },
            label = "Place Description",
            modifier = modifier
        )
        AddPlaceButton(
            isEdit = isEdit,
            isLoading = uiAddEditPlaceState.isLoading,
            addUpdatePlace = { viewModel.addOrUpdatePlace() }
        )
    }
}
