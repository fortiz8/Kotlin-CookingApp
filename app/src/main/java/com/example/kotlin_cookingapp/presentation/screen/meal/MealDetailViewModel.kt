package com.example.kotlin_cookingapp.presentation.screen.meal


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_cookingapp.data.repository.MealRepositoryImpl
import com.example.kotlin_cookingapp.domain.model.MealDetail
import com.example.kotlin_cookingapp.util.MEAL_ID_KEY
import com.example.kotlin_cookingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val repository: MealRepositoryImpl,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _mealDetailState: MutableStateFlow<Resource<MealDetail?>> =
        MutableStateFlow(value = Resource.Loading())
    val mealDetailState: StateFlow<Resource<MealDetail?>>
        get() = _mealDetailState

    private val _mealTitleState = mutableStateOf(value = "")
    val mealTitle: State<String>
        get() = _mealTitleState


    init {
        savedStateHandle.get<String>(key = MEAL_ID_KEY)?.let { id ->
            getMealDetail(id = id)
        }
    }


    private fun getMealDetail(id: String){
        viewModelScope.launch {
            _mealDetailState.value = repository.getMeal(id = id)
        }
    }

    fun setMealTitle(title: String){
        _mealTitleState.value = title
    }
}