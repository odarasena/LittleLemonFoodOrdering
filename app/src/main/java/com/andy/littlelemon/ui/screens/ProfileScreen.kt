package com.andy.littlelemon.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andy.littlelemon.ui.composables.LittleLemonHeader

@Composable
fun ProfileScreen(firstName: String, lastName: String, email: String, onLogoutClicked: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LittleLemonHeader(showUserAvatar = false)
        val firstNameState = rememberSaveable(firstName) { mutableStateOf(firstName) }
        val lastNameState = rememberSaveable(lastName) { mutableStateOf(lastName) }
        val emailState = rememberSaveable(email) { mutableStateOf(email) }
        PersonalInformation(firstNameState, lastNameState, emailState, buttonLabel = "Log Out", onButtonClicked = onLogoutClicked)
    }
}