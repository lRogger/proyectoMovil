package com.example.todolist_mobileproject

import android.os.Bundle
import androidx.activity.ComponentActivity

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

    private val dbHelper by lazy { TodoDbHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_layout) // Renderiza el layout app_layout.xml

        // ---------------------------------------------
        // RecyclerView es un elemento xml que permite mostrar una lista de elementos
        val recyclerView: RecyclerView = findViewById(R.id.list_recycler_view)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        // Crea una lista de elementos a renderizar // Reemplazar por API
        val items: List<Item> = dbHelper.obtenerTareas()

        // Renderiza la lista de elementos
        val adapter = ItemAdapter(items)
        recyclerView.adapter = adapter


        // ---------------------------------------------
        // DETALLES ESTETICOS

        // Eliminar barra de estado celular
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
