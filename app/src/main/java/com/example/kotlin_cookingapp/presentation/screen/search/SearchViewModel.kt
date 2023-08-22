package com.example.kotlin_cookingapp.presentation.screen.search


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_cookingapp.data.repository.MealRepositoryImpl
import com.example.kotlin_cookingapp.domain.model.Meal
import com.example.kotlin_cookingapp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MealRepositoryImpl,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _isLoading = Channel<Boolean>()
    val isLoading: Flow<Boolean>
        get() = _isLoading.receiveAsFlow()

    private val _meals: MutableStateFlow<List<Meal>> = MutableStateFlow(value = emptyList())
    val meals: StateFlow<List<Meal>>
        get() = _meals

    private val _query: MutableStateFlow<String> = MutableStateFlow(value = "")
    val query: StateFlow<String>
        get() = _query

    private val _screenMode: MutableStateFlow<String> = MutableStateFlow(value = "")
    val screenMode: StateFlow<String>
        get() = _screenMode

    private val _searchDetailVisible: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val searchDetailVisible: StateFlow<Boolean>
        get() = _searchDetailVisible


    init {

        savedStateHandle.get<String>(key = SEARCH_SCREEN_MODE_KEY)?.let { mode ->
            _screenMode.value = mode
        }

        savedStateHandle.get<String>(key = SEARCH_SCREEN_FILTER_KEY)?.let { filter ->
            when(filter){
                PC_GAMES -> getMealsByPlatform(filter = PC_GAMES)
                BROWSER_GAMES -> getMealsByPlatform(filter = BROWSER_GAMES)
                LATEST_MEALS -> getLatestMeals()
            }
        }
    }

    fun onSearch(meals: List<Meal>) {
        viewModelScope.launch {
            _isLoading.send(true)
            delay(500)
            _meals.value =
                meals.filter { meal -> meal.title.contains(_query.value, ignoreCase = true) }
            _isLoading.send(false)
        }
    }

    fun clearSearchQuery(){
        _query.value = ""
    }

    fun onQuery(query: String){
        _query.value = query
    }

    fun showSearchDetail(){
        _searchDetailVisible.value = true
    }

    private fun getMealsByPlatform(filter: String) {
        viewModelScope.launch {
            _isLoading.send(true)
            _meals.value = when (val response = repository.getMealsByPlatform(platform = filter)) {
                is Resource.Success -> {
                    response.data ?: emptyList()
                }
                else -> emptyList()
            }
            _isLoading.send(false)
        }
    }

    private fun getLatestMeals() {
        viewModelScope.launch {
            _isLoading.send(true)
            _meals.value = when (val response = repository.sortMeals(criteria = LATEST_MEALS)) {
                is Resource.Success -> response.data ?: emptyList()
                else -> emptyList()
            }
            _isLoading.send(false)
        }
    }

}