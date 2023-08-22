package com.example.kotlin_cookingapp.presentation.screen.base


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kotlin_cookingapp.domain.model.Meal
import com.example.kotlin_cookingapp.presentation.component.drawer.NavigationDrawer
import com.example.kotlin_cookingapp.presentation.component.drawer.NavigationDrawerItem
import com.example.kotlin_cookingapp.presentation.screen.meal.MealDetailScreen
import com.example.kotlin_cookingapp.presentation.screen.meal.MealDetailViewModel
import com.example.kotlin_cookingapp.presentation.screen.home.HomeScreen
import com.example.kotlin_cookingapp.presentation.screen.search.SearchScreen
import com.example.kotlin_cookingapp.presentation.screen.search.SearchViewModel
import com.example.kotlin_cookingapp.util.ALL_MEALS_KEY
import com.example.kotlin_cookingapp.util.Resource
import com.example.kotlin_cookingapp.util.SEARCH_SCREEN_FILTER_KEY
//import com.intuit.sdp.R
import com.example.kotlin_cookingapp.R


@Composable
fun Index(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    availableMeals: Resource<List<Meal>>,
    onOpenDrawer: () -> Unit,
    onSearchButtonClick: () -> Unit,
    onMealClick: (String) -> Unit,
    onPlayTheGameClicked: (String) -> Unit,
    onHomeMenuClick: () -> Unit,
    onPCGamesClick: () -> Unit,
    onWebGamesClick: () -> Unit,
    onLatestMealsClick: () -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        drawerShape = RectangleShape,
        drawerContent = {
            NavigationDrawer(
                header = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(height = dimensionResource(id = com.intuit.sdp.R.dimen._200sdp))
                    ) {
                        Image(
                            modifier = Modifier
                                .size(150.dp)
                                .align(alignment = Alignment.Center),
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "",
                        )
                    }
                },
                content = {
                    NavigationDrawerItem(
                        modifier = Modifier
                            .requiredHeight(45.dp)
                            .padding(5.dp),
                        iconPainter = painterResource(id = R.drawable.ic_back),
                        iconColor = MaterialTheme.colorScheme.primary,
                        text = stringResource(id = R.string.lbl_home),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        textColor = MaterialTheme.colorScheme.onBackground,
                        onClick = onHomeMenuClick
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    NavigationDrawerItem(
                        modifier = Modifier
                            .requiredHeight(45.dp)
                            .padding(5.dp),
                        iconPainter = painterResource(id = R.drawable.ic_back),
                        iconColor = MaterialTheme.colorScheme.primary,
                        text = stringResource(id = R.string.lbl_mexican_food),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        textColor = MaterialTheme.colorScheme.onBackground,
                        onClick = onPCGamesClick
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    NavigationDrawerItem(
                        modifier = Modifier
                            .requiredHeight(45.dp)
                            .padding(5.dp),
                        iconPainter = painterResource(id = R.drawable.ic_back),
                        iconColor = MaterialTheme.colorScheme.primary,
                        text = stringResource(id = R.string.lbl_chinese_food),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        textColor = MaterialTheme.colorScheme.onBackground,
                        onClick = onWebGamesClick
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    NavigationDrawerItem(
                        modifier = Modifier
                            .requiredHeight(45.dp)
                            .padding(5.dp),
                        iconPainter = painterResource(id = R.drawable.ic_back),
                        iconColor = MaterialTheme.colorScheme.primary,
                        text = stringResource(id = R.string.lbl_latest_meals),
                        textStyle = MaterialTheme.typography.bodyMedium,
                        textColor = MaterialTheme.colorScheme.onBackground,
                        onClick = onLatestMealsClick
                    )
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.HomeScreen.route,
        ) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(
                    onOpenDrawer = { onOpenDrawer() },
                    onSearchButtonClick = { onSearchButtonClick() },
                    onMealClick = { mealId ->
                        onMealClick(mealId)
                    },
                    availableMeals = availableMeals
                )
            }
            composable(route = Screen.MealDetailScreen.route) {
                val mealDetailViewModel = hiltViewModel<MealDetailViewModel>()
                MealDetailScreen(
                    viewModel = mealDetailViewModel,
                    navController = navController,
                    onMealClick = { mealUrl ->
                        onPlayTheGameClicked(mealUrl)
                    }
                )
            }
            composable(
                route = Screen.SearchScreen.route,
                arguments = listOf(
                    navArgument(name = SEARCH_SCREEN_FILTER_KEY) {
                        defaultValue = ""
                        type = NavType.StringType
                    }
                )
            ) {
                val viewModel = hiltViewModel<SearchViewModel>()
                val meals =
                    navController.previousBackStackEntry?.savedStateHandle?.get<List<Meal>>(key = ALL_MEALS_KEY)
                        ?: emptyList()
                SearchScreen(
                    viewModel = viewModel,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    meals = meals,
                )
            }
        }
    }
}

