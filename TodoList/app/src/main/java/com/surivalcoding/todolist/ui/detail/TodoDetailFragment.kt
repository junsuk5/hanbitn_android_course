package com.surivalcoding.todolist.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.toRoute
import com.surivalcoding.todolist.R
import com.surivalcoding.todolist.TodoDetail
import com.surivalcoding.todolist.data.Todo
import kotlinx.coroutines.launch
import java.util.Date

class TodoDetailFragment : Fragment(R.layout.fragment_todo_detail) {
    private val viewModel by viewModels<TodoDetailViewModel> {
        TodoDetailViewModel.Factory
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = view.findViewById<CalendarView>(R.id.calendarView)
        val todoTextView = view.findViewById<EditText>(R.id.todoEditText)

        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            viewModel.setDate(year, month, dayOfMonth)
        }

        todoTextView.addTextChangedListener {
            if (it != null && it.toString() != viewModel.uiState.value.todo.title) {
                viewModel.setTodo(it.toString())
            }
        }

        val route = findNavController().getBackStackEntry<TodoDetail>().toRoute<TodoDetail>()

        if (route.id == null) {
            // 추가
            view.findViewById<Button>(R.id.doneButton).setOnClickListener {
                viewModel.addOrUpdateTodo(
                    Todo(title = todoTextView.text.toString())
                )
                findNavController().navigateUp()
            }

        } else {
            // 삭제
            view.findViewById<Button>(R.id.deleteButton).setOnClickListener {
                viewModel.deleteTodo(route.id)
                findNavController().navigateUp()
            }

            // 수정
            view.findViewById<Button>(R.id.doneButton).setOnClickListener {
                val updateTodo = Todo(id = route.id, title = todoTextView.text.toString())
                viewModel.addOrUpdateTodo(updateTodo)
                findNavController().navigateUp()
            }

            // 로드
            viewModel.fetchTodo(route.id)

            // 관찰
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect { state ->
                        todoTextView.setText(state.todo.title)
                        todoTextView.setSelection(state.todo.title.length)

                        calendar.date = state.selectedDate.time
                    }
                }
            }
        }

    }
}