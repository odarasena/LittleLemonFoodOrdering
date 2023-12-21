package com.andy.littlelemon.ui.viewmodel

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andy.littlelemon.data.local.LittleLemonDatabase
import com.andy.littlelemon.data.preferences.EMAIL_KEY
import com.andy.littlelemon.data.preferences.FIRST_NAME_KEY
import com.andy.littlelemon.data.preferences.IS_USER_REGISTERED_KEY
import com.andy.littlelemon.data.preferences.LAST_NAME_KEY
import com.andy.littlelemon.data.preferences.littleLemonPreferences
import com.andy.littlelemon.data.remote.model.MenuItemDTO
import com.andy.littlelemon.data.remote.model.MenuItemsDTO
import com.andy.littlelemon.data.remote.model.convertToMenuItemEntity
import com.andy.littlelemon.ui.navigation.LittleLemonRoutes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LittleLemonViewModel : ViewModel() {

    private val eventsStateFlow = MutableSharedFlow<Event?>()

    val currentEvent = eventsStateFlow.asSharedFlow()

    fun getStartDestination(context: Context): String {
        return if (context.littleLemonPreferences.getBoolean(IS_USER_REGISTERED_KEY, false)) {
            LittleLemonRoutes.Home.route
        } else {
            LittleLemonRoutes.Onboarding.route
        }
    }

    fun register(context: Context, firstName: String, lastName: String, email: String) {
        if (checkOnboardingDataValid(firstName, lastName, email)) {
            context.littleLemonPreferences.edit()
                .putString(FIRST_NAME_KEY, firstName)
                .putString(LAST_NAME_KEY, lastName)
                .putString(EMAIL_KEY, email)
                .putBoolean(IS_USER_REGISTERED_KEY, true)
                .apply()
            sendEvent(Event.RegistrationSuccessfulEvent)
            sendEvent(Event.ToastEvent("Registration successful!"))
        }
    }

    fun logout(context: Context) {
        context.littleLemonPreferences.edit()
            .clear()
            .apply()
        sendEvent(Event.LogOutEvent)
    }

    suspend fun getMenuItems(category: String, searchString: String) = withContext(Dispatchers.IO) {
        if (LittleLemonDatabase.INSTANCE.menuItemDao().isEmpty()) {
            val fetchedItems = fetchMenuItems().map { it.convertToMenuItemEntity() }
            LittleLemonDatabase.INSTANCE.menuItemDao().insertAll(fetchedItems)
        }
        LittleLemonDatabase.INSTANCE.menuItemDao().getAllFromCategory(category, searchString)
    }

    fun getUserPersonalInformation(context: Context): List<String> {
        val firstName = context.littleLemonPreferences.getString(FIRST_NAME_KEY, "") ?: ""
        val lastName = context.littleLemonPreferences.getString(LAST_NAME_KEY, "") ?: ""
        val email = context.littleLemonPreferences.getString(EMAIL_KEY, "") ?: ""
        return listOf(firstName, lastName, email)
    }

    private suspend fun fetchMenuItems(): List<MenuItemDTO> {
        val url = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        val httpClient = HttpClient(Android) {
            install(ContentNegotiation) {
                json(contentType = ContentType("text", "plain"))
            }
        }
        val httpResponse: MenuItemsDTO = httpClient.get(url).body()
        return httpResponse.items
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            eventsStateFlow.emit(event)
        }
    }

    private fun checkOnboardingDataValid(firstName: String, lastName: String, email: String): Boolean {
        var isValid = true
        if (firstName.isBlank()) {
            isValid = false
            sendEvent(Event.ToastEvent("First name is invalid!"))
        }
        if (lastName.isBlank()) {
            isValid = false
            sendEvent(Event.ToastEvent("Last name is invalid!"))
        }
        if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false
            sendEvent(Event.ToastEvent("Email is invalid!"))
        }
        return isValid
    }

}