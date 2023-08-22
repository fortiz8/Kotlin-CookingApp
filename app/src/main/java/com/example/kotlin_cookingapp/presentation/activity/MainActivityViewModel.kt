package com.example.kotlin_cookingapp.presentation.activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_cookingapp.data.repository.MealRepositoryImpl
import com.example.kotlin_cookingapp.domain.model.Meal
import com.example.kotlin_cookingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel is a special type of state holder in charge of giving access to business logic of the app
// Prepares the application data for presentation in a screen.
// We use ViewModel because it has a longer lifetime it can hold data even when configuration changes

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MealRepositoryImpl
): ViewModel() {

    // create an observable of mutable state. mutable at runtime
    private val _splashScreenVisible = mutableStateOf(value = false)
    val splashScreenVisible: State<Boolean>
        get() = _splashScreenVisible

    // MutableStateFlow constantly receiving data and updating
    private val _meals: MutableStateFlow<Resource<List<Meal>>> =
        MutableStateFlow(value = Resource.Loading())
    val meals: StateFlow<Resource<List<Meal>>>
        get() = _meals

    // hide and show splash screen
    init {
        viewModelScope.launch {
            _splashScreenVisible.value = true
            _meals.value = repository.getAllMeals()
            _splashScreenVisible.value = false
        }
    }
}