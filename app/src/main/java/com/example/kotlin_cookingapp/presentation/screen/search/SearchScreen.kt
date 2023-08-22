package com.example.kotlin_cookingapp.presentation.screen.search


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import com.example.kotlin_cookingapp.domain.model.Meal
import com.example.kotlin_cookingapp.util.ALL_MEALS_KEY
import com.example.kotlin_cookingapp.util.SEARCH_MODE_KEY
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    meals: List<Meal>
) {

    val screenMode by viewModel.screenMode
        .collectAsState()

    val isLoading by viewModel.isLoading
        .collectAsState(initial = false)

    val searchDetailVisible by viewModel.searchDetailVisible
        .collectAsState()

    val availableMeals by viewModel.meals
        .collectAsState()

    val searchQuery by viewModel.query
        .collectAsState()

    val scope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.fillMaxSize()) {
        if(screenMode == SEARCH_MODE_KEY){
            SearchMode(
                isLoading = isLoading,
                focusManager = focusManager,
                searchDetailVisible = searchDetailVisible,
                searchSuggestions = availableMeals,
                navController = navController,
                query = searchQuery,
                onClearQuery = {
                    viewModel.clearSearchQuery()
                    navController.popBackStack()
                },
                onSearch = { query ->
                    viewModel.onQuery(query = query)
                    if(query.isNotEmpty()){
                        viewModel.onSearch(meals = meals)
                    }
                },
                search = {
                    if(searchQuery.isNotEmpty()){
                        viewModel.showSearchDetail()
                    }
                }
            )
        } else{
            FilterMode(
                isLoading = isLoading,
                meals = availableMeals,
                onMealClick = { id ->
                    navController.navigate(route = "mealDetail/$id")
                },
                onOpenDrawer = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                },
                onSearchClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = ALL_MEALS_KEY,
                        value = availableMeals
                    )
                    navController.navigate(route = "search?mode=$SEARCH_MODE_KEY")
                }
            )
        }
    }
}