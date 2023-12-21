package com.andy.littlelemon.ui.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andy.littlelemon.data.local.model.MenuItemEntity
import com.andy.littlelemon.ui.screens.HomeScreen
import com.andy.littlelemon.ui.screens.OnboardingScreen
import com.andy.littlelemon.ui.screens.ProfileScreen
import com.andy.littlelemon.ui.viewmodel.Event
import com.andy.littlelemon.ui.viewmodel.LittleLemonViewModel

@Composable
fun LittleLemonNavHost(navController: NavHostController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val littleLemonViewModel: LittleLemonViewModel = viewModel()

    NavHost(navController = navController, startDestination = littleLemonViewModel.getStartDestination(context)) {
        composable(LittleLemonRoutes.Onboarding.route) {
            OnboardingScreen { firstName, lastName, email ->
                littleLemonViewModel.register(context, firstName, lastName, email)
            }
        }
        composable(LittleLemonRoutes.Home.route) {
            val categories = listOf("Starters", "Mains", "Desserts", "Drinks")
            val selectedCategory = rememberSaveable { mutableStateOf(categories.first()) }
            val searchString = rememberSaveable { mutableStateOf("") }

            val menuItemsFiltered = produceState(initialValue = emptyList<MenuItemEntity>(), selectedCategory.value, searchString.value) {
                value = littleLemonViewModel.getMenuItems(selectedCategory.value, searchString.value)
            }

            HomeScreen(
                categories = categories,
                selectedCategory = selectedCategory,
                searchString = searchString,
                menuItemsFiltered = menuItemsFiltered.value,
                onUserAvatarClicked = {
                    navController.navigate(LittleLemonRoutes.Profile.route)
                }
            )
        }
        composable(LittleLemonRoutes.Profile.route) {
            val userPersonalInformation = littleLemonViewModel.getUserPersonalInformation(context)
            ProfileScreen(
                firstName = userPersonalInformation[0],
                lastName = userPersonalInformation[1],
                email = userPersonalInformation[2],
                onLogoutClicked = {
                    littleLemonViewModel.logout(context)
                }
            )
        }
    }

    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            littleLemonViewModel.currentEvent.collect {
                when (it) {
                    is Event.RegistrationSuccessfulEvent -> {
                        navController.navigate(LittleLemonRoutes.Home.route) {
                            popUpTo(LittleLemonRoutes.Onboarding.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    }

                    is Event.ToastEvent -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }

                    is Event.LogOutEvent -> {
                        navController.navigate(LittleLemonRoutes.Onboarding.route) {
                            popUpTo(LittleLemonRoutes.Home.route) {
                                inclusive = true
                            }
                        }
                    }

                    else -> {

                    }
                }
            }
        }
    }
}