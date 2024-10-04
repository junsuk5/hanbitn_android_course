package com.surivalcoding.todolist.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.surivalcoding.todolist.R
import com.surivalcoding.todolist.TodoDetail
import com.surivalcoding.todolist.databinding.FragmentTodoListBinding
import com.surivalcoding.todolist.ui.list.adapter.TodoListAdapter
import kotlinx.coroutines.launch

class TodoListFragment : Fragment(R.layout.fragment_todo_list) {
    private val viewModel by viewModels<TodoListViewModel> {
        TodoListViewModel.Factory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val todoListAdapter = TodoListAdapter { todo ->
            // 클릭시 처리
            findNavController().navigate(route = TodoDetail(id = todo.id))
        }
        recyclerView.adapter = todoListAdapter

        view.findViewById<FloatingActionButton>(R.id.addButton).setOnClickListener {
            findNavController().navigate(route = TodoDetail())
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    todoListAdapter.submitList(state.todos)
                }
            }
        }
    }
}