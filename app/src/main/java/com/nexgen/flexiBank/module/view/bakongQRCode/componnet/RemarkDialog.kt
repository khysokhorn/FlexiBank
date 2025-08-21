package com.nexgen.flexiBank.module.view.bakongQRCode.componnet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.utils.theme.BackgroundColor

@Composable
fun RemarkDialog(
    showDialog: Boolean, initialValue: String = "", onDismiss: () -> Unit, onSave: (String) -> Unit
) {
    var remark by remember { mutableStateOf(initialValue) }
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            ),
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextField(
                        value = remark,
                        onValueChange = { remark = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0x4D000000), shape = RoundedCornerShape(32.dp)
                            ),
                        placeholder = {
                            Text(
                                text = "Remark",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingIcon = {
                            Icon(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        remark = ""
                                    })
                                    .padding(start = 8.dp),
                                painter = painterResource(id = R.drawable.ic_message),
                                contentDescription = "Message Icon",
                                tint = Color.White
                            )
                        },
                        trailingIcon = {
                            Box(
                                modifier = Modifier
                                    .clickable(onClick = {
                                        remark = ""
                                        onSave("")
                                    })
                                    .background(
                                        color = BackgroundColor.copy(alpha = 0.4f),
                                        shape = CircleShape
                                    )
                                    .padding(1.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.close_24px),
                                    contentDescription = "Check Icon",
                                    tint = Color.White,
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = false,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onSave(remark)
                            })
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RemarkDialogPreview() {
    RemarkDialog(
        showDialog = true,
        initialValue = "This is a test remark",
        onDismiss = {},
        onSave = {})
}