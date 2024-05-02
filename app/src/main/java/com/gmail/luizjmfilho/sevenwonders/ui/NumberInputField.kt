package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

@VisibleForTesting
const val NumberInputFieldNumberTestTag = "NumberInputFieldNumber"

/**
 * An input field which allows a number to be set. The number can be increased and decreased
 * by tapping on the buttons "+" and "-".
 *
 * @param number The number to be displayed.
 * @param textColor The color of the input field text.
 * @param onNumberChange Called when the input field value changes (e.g when the user taps on the "+"or "-" buttons).
 * @param modifier The [Modifier] to be applied to this input field.
 * @param backgroundColor The background color of the input field.
 * @param shape The input field shape.
 */
@Composable
fun NumberInputField(
    number: Int,
    textColor: Color,
    onNumberChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    shape: Shape = CardDefaults.shape,
) {
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = textColor,
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.CenterHorizontally),
        ) {
            IconButton(
                onClick = { onNumberChange(number - 1) },
            ) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = stringResource(R.string.decrease_number_button_content_description),
                    tint = Color(0xFFA2A0A0),
                )
            }

            Text(
                text = number.toString(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag(NumberInputFieldNumberTestTag),
            )

            IconButton(
                onClick = { onNumberChange(number + 1) },
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    tint = Color(0xFFA2A0A0),
                    contentDescription = stringResource(R.string.increase_number_button_content_description),
                )
            }
        }
    }
}

@Preview
@Composable
private fun NumberInputFieldPreview() {
    SevenWondersTheme {
        NumberInputField(
            number = 42,
            textColor = Color.Black,
            onNumberChange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}