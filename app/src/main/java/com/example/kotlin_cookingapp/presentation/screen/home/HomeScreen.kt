package com.example.kotlin_cookingapp.presentation.screen.home


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kotlin_cookingapp.R
import com.example.kotlin_cookingapp.domain.model.Meal
import com.example.kotlin_cookingapp.presentation.component.CarouselView
import com.example.kotlin_cookingapp.presentation.component.WarningMessage
import com.example.kotlin_cookingapp.presentation.component.FoodCard
import com.example.kotlin_cookingapp.presentation.component.TopBar
import com.example.kotlin_cookingapp.util.Resource
import com.example.kotlin_cookingapp.util.getUrls
import com.example.kotlin_cookingapp.util.header

@Composable
fun HomeScreen(
    onOpenDrawer: () -> Unit,
    onSearchButtonClick: () -> Unit,
    onMealClick: (String) -> Unit,
    availableMeals: Resource<List<Meal>>
) {
    availableMeals.data?.let { meals ->

        val screenHeight = LocalContext.current.resources.displayMetrics.heightPixels.dp /
                LocalDensity.current.density

        if(meals.isEmpty()){
            WarningMessage(textId = R.string.wrn_no_meals)
        }
        else{
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopBar(
                    onOpenDrawer = onOpenDrawer,
                    onSearchButtonClick = onSearchButtonClick
                )

                LazyVerticalGrid(columns = GridCells.Fixed(count = 2)){
                    header{
                        CarouselView(
                            modifier = Modifier
                                .requiredHeight(height = 260.dp)
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 12.dp),
                            urls = meals.getUrls(),
                            shape = MaterialTheme.shapes.medium,
                            crossFade = 1000
                        )
                        Spacer(modifier = Modifier.height(30.dp ))
                    }
                    items(items = meals){ meal ->
                        FoodCard(
                            modifier = Modifier
                                .padding(all = 8.dp)
                                .requiredHeight(height = screenHeight * 0.45f),
                            meal = meal,
                            onClick = { onMealClick(meal.id) }
                        )
                    }
                }
            }
        }
    }

}