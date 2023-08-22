package com.example.kotlin_cookingapp.data.remote.dto

import com.example.kotlin_cookingapp.domain.model.Meal
import com.google.gson.annotations.SerializedName

data class MealDto(
    @SerializedName("description")
    val shortDescription: String,
    @SerializedName("forked")
    val mealUrl: String?,
    val id: String,
    @SerializedName("image")
    val thumbnail: String,
    val ingredient: List<IngredientDto>,
    val ingredientGroup: List<IngredientGroupDto>?,
    @SerializedName("name")
    val title: String,
    val notes: String?,
    val step: List<StepDto>,
    val tag: List<String>,
    val screenShots: List<ScreenshotDto>?,
) {
    fun toMeal(): Meal {
        return Meal(
            shortDescription,
            mealUrl,
            id,
            thumbnail,
            ingredient,
            ingredientGroup,
            title,
            notes,
            step,
            tag,
            screenShots
        )
    }
}
