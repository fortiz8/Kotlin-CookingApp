package com.example.kotlin_cookingapp.util

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import com.example.kotlin_cookingapp.domain.model.Meal

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(
        span = { GridItemSpan(maxLineSpan) },
        content = content
    )
}

fun List<Meal>.getUrls(): List<String>{
    return takeRandomElements(numberOfElements = 3).map {it.thumbnail }
}

fun <T> List<T>.takeRandomElements(numberOfElements:Int): List<T> {
    return if(numberOfElements > size) this
    else asSequence().shuffled().take(numberOfElements).toList()
}