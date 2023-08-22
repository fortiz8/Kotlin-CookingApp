package com.example.kotlin_cookingapp.presentation.screen.base


sealed class Screen(
    val route: String
) {
    object HomeScreen: Screen("home")
    object MealDetailScreen: Screen("mealDetail/{id}")
    object SearchScreen: Screen(
        route = "search?mode={mode}&filter={filter}"
    )

}
