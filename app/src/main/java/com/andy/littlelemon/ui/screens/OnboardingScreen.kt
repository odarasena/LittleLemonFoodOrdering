package com.andy.littlelemon.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andy.littlelemon.ui.composables.LittleLemonHeader
import com.andy.littlelemon.ui.theme.HighlightBlack
import com.andy.littlelemon.ui.theme.HighlightGray
import com.andy.littlelemon.ui.theme.PrimaryGreen
import com.andy.littlelemon.ui.theme.PrimaryYellow

@Composable
fun OnboardingScreen(onRegisterClicked: (String, String, String) -> Unit) {
    val firstName = rememberSaveable { mutableStateOf("") }
    val lastName = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LittleLemonHeader(showUserAvatar = false)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(PrimaryGreen),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Let's get to know you",
                style = MaterialTheme.typography.headlineLarge,
                color = HighlightGray
            )
        }

        PersonalInformation(
            firstName = firstName,
            lastName = lastName,
            email = email,
            buttonLabel = "Register",
            onButtonClicked = {
                onRegisterClicked(firstName.value, lastName.value, email.value)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInformation(
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    email: MutableState<String>,
    canUserEditText: Boolean = true,
    buttonLabel: String,
    onButtonClicked: () -> Unit
) {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Personal Information",
            textAlign = TextAlign.Start,
            color = HighlightBlack,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = firstName.value,
            onValueChange = {
                firstName.value = it
            },
            readOnly = !canUserEditText,
            label = { Text(text = "First Name") },
            singleLine = true,
            placeholder = { Text(text = "Andrew") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = lastName.value,
            onValueChange = {
                lastName.value = it
            },
            readOnly = !canUserEditText,
            label = { Text(text = "Last Name") },
            singleLine = true,
            placeholder = { Text(text = "Doe") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = email.value,
            onValueChange = {
                email.value = it
            },
            readOnly = !canUserEditText,
            label = { Text(text = "Email") },
            singleLine = true,
            placeholder = { Text(text = "johndoe@gmail.com") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedLabelColor = PrimaryGreen,
                focusedBorderColor = PrimaryGreen
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryYellow, contentColor = HighlightBlack),
        ) {
            Text(text = buttonLabel)
        }
    }
}