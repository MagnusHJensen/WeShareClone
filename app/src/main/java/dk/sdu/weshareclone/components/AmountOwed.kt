package dk.sdu.weshareclone.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.sdu.weshareclone.ui.theme.WeShareTheme

@Composable
fun AmountOwed(amount: Int, modifier: Modifier = Modifier, includeLabel: Boolean = true) {
    val labelText = if (amount == 0) "You are all caught up with your expenses" else if (amount > 0) "You owe" else "You are waiting for"
    val textColor = if (amount == 0) Color.Black else if (amount > 0) Color.Red else Color.Green
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        if (includeLabel) {
            Text(text = labelText)
        }
        if (amount != 0) {
            Text(text = amount.toString(), color = textColor, modifier = modifier)
        }
    }

}

@Composable
@Preview
fun AmountOwedPreview() {
    WeShareTheme {
        AmountOwed(100, includeLabel = false)
    }
}

@Composable
@Preview
fun AmountOwedPreviewWithLabel() {
    WeShareTheme {
        AmountOwed(-100)
    }
}


@Composable
@Preview
fun AmountOwedPreviewWithLabelAnd0() {
    WeShareTheme {
        AmountOwed(0)
    }
}