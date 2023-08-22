package com.example.kotlin_cookingapp.domain.repository

import com.example.kotlin_cookingapp.domain.model.Meal
import com.example.kotlin_cookingapp.domain.model.MealDetail
import com.example.kotlin_cookingapp.util.Resource

interface MealRepository {
    suspend fun getAllMeals(): Resource<List<Meal>>

    suspend fun getMeal(id: String): Resource<MealDetail?>

    suspend fun getMealsByPlatform(platform :String): Resource<List<Meal>>

    suspend fun sortMeals(criteria: String): Resource<List<Meal>>
}