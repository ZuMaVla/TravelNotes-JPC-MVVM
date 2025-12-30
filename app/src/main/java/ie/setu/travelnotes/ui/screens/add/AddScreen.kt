package ie.setu.travelnotes.ui.screens.add

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Label
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import ie.setu.travelnotes.firebase.firestore.localDate
import ie.setu.travelnotes.firebase.firestore.toMillis
import ie.setu.travelnotes.ui.components.add.AddPlaceButton
import ie.setu.travelnotes.ui.components.add.AddText
import ie.setu.travelnotes.ui.components.add.EditText
import ie.setu.travelnotes.ui.components.add.TextInput
import ie.setu.travelnotes.ui.components.add.TravelDatePicker
import ie.setu.travelnotes.ui.components.general.ShowPhotoPicker
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(modifier: Modifier = Modifier,
              isEdit: Boolean,
              viewModel: AddViewModel = hiltViewModel(),
              onPlaceUpdateSuccess: () -> Unit,
              navigateUp: () -> Unit,

) {
    Timber.i("isEditing: $isEdit")
    val uiAddEditPlaceState = viewModel.uiPlaceState.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getPlace(viewModel.placeId)
    }
    LaunchedEffect(uiAddEditPlaceState.isSaved) {
        if (uiAddEditPlaceState.isSaved) {
            viewModel.onPlaceAdded()
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

        Column {
            Row (
                modifier = modifier,
                verticalAlignment = CenterVertically
            )
            {
                Text(text = "Public?")
                Checkbox(
                    checked = uiAddEditPlaceState.public,
                    onCheckedChange = { viewModel.onPublicChange(it) },
                )
                AddPlaceButton(
                    modifier = modifier.fillMaxWidth(),
                    isEdit = isEdit,
                    isLoading = uiAddEditPlaceState.isLoading,
                    addUpdatePlace = { viewModel.addOrUpdatePlace() }
                )

            }
        }



        TravelDatePicker(
            value = uiAddEditPlaceState.selectedDate.localDate().toMillis(),
            onDateSelected = { viewModel.onDateChange(it.toMillis()) }
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
        Column {
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShowPhotoPicker(
                        onPhotoUriChanged = {
                            viewModel.onPhotoUriChange(it)
                        },
                        modifier = modifier
                            .fillMaxWidth()
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = modifier
                            .fillMaxWidth(),
                        onClick = {
                            Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Text("Set Location")
                    }
                }
            }
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                )  {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = uiAddEditPlaceState.imageToDisplay,
                            contentDescription = "Place Image",
                            modifier = modifier
                                .fillMaxWidth()
                        )
                    }

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally

                )  {

                }
            }
        }
    }
}
