package com.andy.littlelemon.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.andy.littlelemon.R
import com.andy.littlelemon.ui.theme.PrimaryYellow


@Composable
fun LittleLemonHeader(modifier: Modifier = Modifier, showUserAvatar: Boolean, onUserAvatarClicked: () -> Unit = {}) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .align(Alignment.Center)
                .padding(16.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo"
        )
        if (showUserAvatar) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(60.dp),
                onClick = onUserAvatarClicked
            ) {
                Icon(
                    modifier = Modifier.size(60.dp),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Avatar",
                    tint = PrimaryYellow
                )
            }
        }
    }
}