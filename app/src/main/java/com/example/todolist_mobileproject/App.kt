package com.example.todolist_mobileproject

import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.util.Log
import android.widget.Button

// Renderizar elementos mutables
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Elementos de la app
import com.example.todolist_mobileproject.adapter.ItemAdapter
import com.example.todolist_mobileproject.model.Item

// Eliminar barra de estado celular
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todolist_mobileproject.db.TodoDbHelper


class App : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_layout)
        initComponents()
        initListeners()
        initUI()
    }

    private lateinit var btnAdd: Button
    private val dbHelper by lazy { TodoDbHelper(this) }

    private fun initComponents() {
        btnAdd = findViewById(R.id.add_item_button)
    }

    private fun initListeners() {
        btnAdd.setOnClickListener{

        }
    }

    private fun initUI() {
        eliminarBarra()
        cargarData()
    }

    private fun eliminarBarra(){
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cargarData(){
        val recyclerView: RecyclerView = findViewById(R.id.list_recycler_view)
        val linearLayoutManager = LinearLayoutManager(this)
        val items: List<Item> = dbHelper.obtenerTareas()
        val adapter = ItemAdapter(items, dbHelper)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }
}
