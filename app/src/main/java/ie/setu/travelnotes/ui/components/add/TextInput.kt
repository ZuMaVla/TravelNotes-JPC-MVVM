package ie.setu.travelnotes.ui.components.add

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ie.setu.travelnotes.ui.theme.TravelNotesTheme

@Composable
fun TextInput(
//    value: String,
    onTextChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        ),
        maxLines = 1,
        value = text,
        onValueChange = {
            text = it
            onTextChange(text)
        },
        label = { Text(text = label) },
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun MessagePreview() {
    TravelNotesTheme {
        TextInput(
//            value = "",
            onTextChange = {},
            label = "Label",
            modifier = Modifier,
            )
    }
}