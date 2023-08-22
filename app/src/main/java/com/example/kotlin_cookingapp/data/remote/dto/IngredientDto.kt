package com.example.kotlin_cookingapp.data.remote.dto

import com.example.kotlin_cookingapp.domain.model.Ingredient

data class IngredientDto(
    val amount: String,
    val name: String,
    val unit: String
) {
    fun toIngredient(): Ingredient {
        return Ingredient(
            amount,
            name,
            unit,
        )
    }
}