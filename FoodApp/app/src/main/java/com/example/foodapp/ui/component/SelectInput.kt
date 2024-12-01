package com.example.foodapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SelectInput(selected: Boolean = false, onSelectedChange: (String) -> Unit) {

    Row(
        modifier = Modifier
            .width(130.dp)
            .padding(2.dp)
            .height(30.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
            .background(MaterialTheme.colorScheme.secondary)
            ,
        verticalAlignment = Alignment.CenterVertically

    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(corner = CornerSize(20.dp)))
                .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable{onSelectedChange("")},

            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = "Lojas",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = if (selected) Color.White else Color.Black
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
                .clip(RoundedCornerShape(corner = CornerSize(20.dp)))
                .background(if (!selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable { onSelectedChange("") }
            ,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = "Items",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = if (!selected) Color.White else Color.Black
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StylishSelect(
    value: TextFieldValue,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    label: String,
    placeholder: String,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current


    Column(
        modifier = Modifier
            .width(170.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            placeholder = { Text(placeholder) },
            trailingIcon = {
                if (expanded) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Dropdown Arrow"
                    )
                }else{
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown Arrow"
                    )
                }
            },
            singleLine = true,
            modifier = Modifier
                .onFocusChanged { expanded = it.isFocused },
            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedLabelColor = Color.Transparent,
                cursorColor = Color.Transparent
            ),
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()

            },
            modifier = Modifier.fillMaxWidth(0.9f).background(MaterialTheme.colorScheme.secondary).height(250.dp),
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                        focusManager.clearFocus()
                    },
                    contentPadding = PaddingValues(vertical = 16.dp)
                )
            }
        }
    }
}