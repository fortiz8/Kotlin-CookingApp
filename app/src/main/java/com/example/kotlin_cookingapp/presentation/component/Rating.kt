package com.example.kotlin_cookingapp.presentation.component


import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.kotlin_cookingapp.R

@Composable
fun Rating(text: String) {
    val resource = if(text.contains("fiveStars", ignoreCase = true)){
        R.drawable.ic_circle_info_solid
    } else if (text.contains("fourStars", ignoreCase = true)) {
        R.drawable.ic_circle_info_solid
    } else if (text.contains("threeStars", ignoreCase = true)) {
        R.drawable.ic_circle_info_solid
    } else if (text.contains("twoStars", ignoreCase = true)) {
        R.drawable.ic_circle_info_solid
    } else if (text.contains("oneStar", ignoreCase = true)){
        R.drawable.ic_circle_info_solid
    } else {
        R.drawable.ic_warning
    }

    Icon(
        painter = painterResource(id = resource),
        contentDescription = "",
        tint = MaterialTheme.colors.primaryVariant
    )
}