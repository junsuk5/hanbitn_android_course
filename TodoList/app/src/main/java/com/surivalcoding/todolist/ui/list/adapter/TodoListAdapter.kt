package com.surivalcoding.todolist.ui.list.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.surivalcoding.todolist.data.Todo
import com.surivalcoding.todolist.databinding.ItemTodoBinding

class TodoListAdapter(
    private val onClick: (Todo) -> Unit
) : ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(
    TodoDiffUtilCallback()
) {
    private lateinit var binding: ItemTodoBinding

    class TodoViewHolder(
        private val binding: ItemTodoBinding,
        private val onClick: (Todo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.text2.text = todo.title
            binding.text1.text = DateFormat.format("yyyy/MM/dd", todo.date)
        }

        fun setOnClickListener(todo: Todo) {
            binding.root.setOnClickListener {
                onClick(todo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.setOnClickListener(getItem(position))
    }
}

class TodoDiffUtilCallback: DiffUtil.ItemCallback<Todo>() {
    // 두 아이템이 동일한 아이템인지 체크
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.id == newItem.id
    }

    // 두 아이템의 내용이 동일한지 체크
    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}