package com.example.kotlin_cookingapp.presentation.screen.search


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlin_cookingapp.domain.model.Meal
import com.example.kotlin_cookingapp.presentation.component.CarouselView
import com.example.kotlin_cookingapp.presentation.component.SearchDetailCard
import com.example.kotlin_cookingapp.presentation.component.TopBar
import com.example.kotlin_cookingapp.util.getUrls

@Composable
fun FilterMode(
    meals: List<Meal>,
    isLoading: Boolean,
    onMealClick: (String) -> Unit,
    onOpenDrawer: () -> Unit,
    onSearchClick: () -> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 15.dp),
        modifier = Modifier.fillMaxSize()
    ){

        item{
            TopBar(
                onOpenDrawer = onOpenDrawer,
                onSearchButtonClick = onSearchClick
            )
        }

        item {
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

        if(isLoading){
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
        } else{
            items(items = meals){ meal ->
                Box(modifier = Modifier.padding(horizontal = 8.dp)){
                    SearchDetailCard(
                        meal = meal,
                        onClick = onMealClick
                    )
                }
            }
        }
    }

}