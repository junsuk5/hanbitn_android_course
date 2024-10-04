package com.surivalcoding.todolist.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.surivalcoding.todolist.MyApp
import com.surivalcoding.todolist.data.Todo
import com.surivalcoding.todolist.data.TodoDao
import com.surivalcoding.todolist.ui.list.TodoListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class TodoDetailViewModel(
    private val todoDao: TodoDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun setDate(year: Int, month: Int, day: Int) {
        val calendar = Calendar.getInstance().apply {
            set(year, month, day)
        }
        _uiState.update { state ->
            state.copy(selectedDate = calendar.time)
        }
    }

    fun setTodo(title: String) {
        _uiState.update { state ->
            state.copy(
                todo = state.todo.copy(title = title)
            )
        }
    }

    fun fetchTodo(id: Long) {
        viewModelScope.launch {
            todoDao.getTodo(id).collect { todo ->
                todo?.let {
                    _uiState.update { state ->
                        state.copy(
                            todo = todo,
                            selectedDate = Date(todo.date),
                        )
                    }
                }
            }
        }
    }

    fun addOrUpdateTodo(todo: Todo) {
        val addTodo = todo.copy(date = uiState.value.selectedDate.time)
        viewModelScope.launch {
            todoDao.upsert(addTodo)
        }
    }

    fun deleteTodo(id: Long) {
        viewModelScope.launch {
            todoDao.delete(id)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras,
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as MyApp
                return TodoDetailViewModel(application.db.todoDao()) as T
            }
        }
    }
}

data class TodoDetailUiState(
    val selectedDate: Date = Calendar.getInstance().time,
    val todo: Todo = Todo(title = ""),
)