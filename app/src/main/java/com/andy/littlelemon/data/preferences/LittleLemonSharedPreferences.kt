package com.andy.littlelemon.data.preferences

import android.content.Context

val Context.littleLemonPreferences
    get() = getSharedPreferences("littleLemon", Context.MODE_PRIVATE)

const val FIRST_NAME_KEY = "firstNameKey"
const val LAST_NAME_KEY = "lastNameKey"
const val EMAIL_KEY = "emailKey"
const val IS_USER_REGISTERED_KEY = "isUserRegisteredKey"
