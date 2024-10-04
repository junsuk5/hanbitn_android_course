package com.surivalcoding.todolist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.surivalcoding.todolist.data.Todo
import com.surivalcoding.todolist.databinding.ActivityMainBinding
import com.surivalcoding.todolist.ui.detail.TodoDetailFragment
import com.surivalcoding.todolist.ui.list.TodoListFragment
import kotlinx.serialization.Serializable

@Serializable
data object TodoList

@Serializable
data class TodoDetail(val id: Long? = null)

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController.graph = navController.createGraph(
            startDestination = TodoList
        ) {
            fragment<TodoListFragment, TodoList> {
                label = "TodoList"
            }
            fragment<TodoDetailFragment, TodoDetail> {
                label = "TodoDetail"
            }
        }
    }
}