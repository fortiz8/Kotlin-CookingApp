package com.example.kotlin_cookingapp.domain.model

import com.example.kotlin_cookingapp.data.remote.dto.IngredientDto
import com.example.kotlin_cookingapp.data.remote.dto.IngredientGroupDto
import com.example.kotlin_cookingapp.data.remote.dto.ScreenshotDto
import com.example.kotlin_cookingapp.data.remote.dto.StepDto

data class MealDetail(
    val shortDescription: String,
    val mealUrl: String?,
    val id: String,
    val thumbnail: String,
    val ingredient: List<IngredientDto>,
    val ingredientGroup: List<IngredientGroupDto>?,
    val title: String,
    val notes: String?,
    val step: List<StepDto>,
    val tag: List<String>,
    val screenShots: List<ScreenshotDto>?
)