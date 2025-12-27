package ie.setu.travelnotes.ui.components.add

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.travelnotes.firebase.firestore.localDate
import ie.setu.travelnotes.ui.theme.TravelNotesTheme
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.ExperimentalTime
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelDatePicker(
    value: Long,                         // from VM
    onDateSelected: (LocalDate) -> Unit,       // goes to VM
    label: String = "Pick a date",
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = value
    )

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    OutlinedTextField(
        value = value.localDate().format(formatter),
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "Select date")
            }
        },
        modifier = modifier.fillMaxWidth()
    )

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    val pickedMillis = datePickerState.selectedDateMillis
                    if (pickedMillis != null) onDateSelected(millisToLocalDate(pickedMillis))   // callback to screen + VM
                    showDialog = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

private fun LocalDate.toMillis(): Long {
    return this.atStartOfDay(ZoneId.of("UTC"))
        .toInstant()
        .toEpochMilli()
}



private fun millisToLocalDate(millis: Long): LocalDate {
    return Instant.ofEpochMilli(millis)
        .atZone(ZoneId.of("UTC"))
        .toLocalDate()
}


@Preview(showBackground = true)
@Composable
fun DatePickerPreview() {
    TravelNotesTheme {
        TravelDatePicker(
            value = LocalDate.now().toMillis(),
            onDateSelected = {}
        )
    }
}