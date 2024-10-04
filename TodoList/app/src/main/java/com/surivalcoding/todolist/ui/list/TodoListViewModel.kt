package com.surivalcoding.todolist.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.surivalcoding.todolist.MyApp
import com.surivalcoding.todolist.data.Todo
import com.surivalcoding.todolist.data.TodoDao
import com.surivalcoding.todolist.ui.detail.TodoDetailViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val todoDao: TodoDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            todoDao.getAll().collect {
                _uiState.update { state ->
                    state.copy(todos = it)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras,
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as MyApp
                return TodoListViewModel(application.db.todoDao()) as T
            }
        }
    }
}

data class TodoListUiState(
    val todos: List<Todo> = emptyList()
)