package com.andy.littlelemon.ui.viewmodel

sealed class Event {

    data object RegistrationSuccessfulEvent : Event()
    data object LogOutEvent: Event()
    data class ToastEvent(val message: String) : Event()

}