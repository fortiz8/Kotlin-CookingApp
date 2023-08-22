package com.example.kotlin_cookingapp.data.remote.dto

import com.example.kotlin_cookingapp.domain.model.IngredientGroup

data class IngredientGroupDto(
    val ingredient: List<Any>
){
    fun toIngredientGroup(): IngredientGroup {
        return IngredientGroup(
            ingredient
        )
    }
}