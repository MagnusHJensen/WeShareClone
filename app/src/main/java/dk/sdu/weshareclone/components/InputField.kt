package dk.sdu.weshareclone.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InputField(label: String, labelModifier: Modifier = Modifier, value: String, onValueChange: (String) -> Unit, fieldModifier: Modifier = Modifier) {
    Column {
        Text(text = label, modifier = labelModifier)
        TextField(value = value, onValueChange = onValueChange, modifier = fieldModifier)
    }
}