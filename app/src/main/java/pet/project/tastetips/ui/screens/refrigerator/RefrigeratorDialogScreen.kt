package pet.project.tastetips.ui.screens.refrigerator

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import pet.project.tastetips.R
import pet.project.tastetips.model.ProductDateTransformation
import pet.project.tastetips.data.RefrigeratorIcon
import pet.project.tastetips.ui.theme.textFieldFocusedColor
import pet.project.tastetips.ui.theme.textFieldUnfocusedColor

@Composable
fun RefrigeratorDialog(
    onDismissRequest: () -> Unit,
    onConfirmationRequest: () -> Unit,
    updateCurrentIcon: (Int) -> Unit,
    onNameFieldValueChange: (String) -> Unit,
    onDateFieldValueChange: (String) -> Unit,
    getIconsList: (SnapshotStateList<RefrigeratorIcon>) -> Unit,
    text: String,
    date: String
) {
    val currentIcons = remember { mutableStateListOf<RefrigeratorIcon>() }

    getIconsList(currentIcons)

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Enter values to add",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                        .padding(vertical = 12.dp)
                )
                LazyRow(modifier = Modifier.padding(start = 16.dp)) {
                    itemsIndexed(currentIcons) { index, item ->
                        IconButton(
                            onClick = {
                                updateCurrentIcon(index)
                                getIconsList(currentIcons)
                            }
                        ) {
                            if (item.isChosen) {
                                Icon(imageVector = item.filledIcon,
                                    contentDescription = null,
                                    tint = textFieldFocusedColor)
                            }
                            else {
                                Icon(item.outlinedIcon, null)
                            }
                        }
                    }
                }
                RefrigeratorDialogTextField(
                    name = R.string.name,
                    value = text,
                    onValueChange = onNameFieldValueChange
                )
                RefrigeratorDatedTextField(
                    name = R.string.exp_date,
                    value = date,
                    onValueChange = onDateFieldValueChange
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss")
                    }
                    TextButton(
                        onClick = { onConfirmationRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@Composable
fun RefrigeratorDialogTextField(
    @StringRes name: Int,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(name)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = textFieldUnfocusedColor,
            unfocusedContainerColor = textFieldUnfocusedColor,
        )
    )
}


@Composable
fun RefrigeratorDatedTextField(
    @StringRes name: Int,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(name)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = textFieldUnfocusedColor,
            unfocusedContainerColor = textFieldUnfocusedColor,
        ),
        visualTransformation = ProductDateTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}