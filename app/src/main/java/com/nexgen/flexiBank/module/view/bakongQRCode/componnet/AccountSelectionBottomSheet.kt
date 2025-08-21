package com.nexgen.flexiBank.module.view.bakongQRCode.componnet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.module.view.bakongQRCode.model.Account
import com.nexgen.flexiBank.module.view.utils.text.InterNormal
import com.nexgen.flexiBank.utils.theme.BackgroundColor
import com.nexgen.flexiBank.utils.theme.Black
import com.nexgen.flexiBank.utils.theme.Blue
import com.nexgen.flexiBank.utils.theme.Hint
import com.nexgen.flexiBank.utils.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSelectionBottomSheet(
    show: Boolean,
    accounts: List<Account>,
    selectedAccountId: String,
    onAccountSelected: (Account) -> Unit,
    onDismiss: () -> Unit
) {
    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            containerColor = White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            dragHandle = {
                BottomSheetDefaults.DragHandle(
                    color = Hint,
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Title
                Text(
                    text = "Accounts",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = InterNormal,
                        fontWeight = FontWeight.W600,
                        color = Black,
                    ),
                    modifier = Modifier.padding(bottom = 28.dp)
                )

                accounts.forEach { account ->
                    AccountItem(
                        account = account,
                        isSelected = account.id == selectedAccountId,
                        onClick = {
                            onAccountSelected(account)
                            onDismiss()
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Bottom spacing for home indicator
                Spacer(modifier = Modifier.height(34.dp))
            }
        }
    }
}

@Composable
private fun AccountItem(
    account: Account,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                color = if (isSelected) {
                    Blue.copy(alpha = 0.1f)
                } else {
                    White
                }
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Account Icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Blue else Black),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = account.iconRes),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = account.name,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = InterNormal,
                            fontWeight = FontWeight.W600,
                            color = Black,
                        )
                    )
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(BackgroundColor)
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.img_tick),
                                contentDescription = "Default Account",
                                tint = Blue,
                                modifier = Modifier.size(10.dp)
                            )
                        }
                    }
                    if (account.hasVisa) {
                        Image(
                            painter = painterResource(R.drawable.img_acount),
                            contentDescription = "Visa Card",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = account.number,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = InterNormal,
                            fontWeight = FontWeight.W400,
                            color = Hint,
                        )
                    )
                    Text(
                        text = "|",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = InterNormal,
                            fontWeight = FontWeight.W400,
                            color = Hint,
                        )
                    )
                    Text(
                        text = account.balance,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = InterNormal,
                            fontWeight = FontWeight.W400,
                            color = Black,
                        )
                    )
                }
            }
        }
    }
}
