package com.example.kotlin_cookingapp.data.remote.dto

import com.example.kotlin_cookingapp.domain.model.Step

data class StepDto(
    val description: String
) {
    fun toStep(): Step {
        return Step(
            description
        )
    }
}