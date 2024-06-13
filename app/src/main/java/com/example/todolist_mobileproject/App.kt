package com.example.todolist_mobileproject

import android.os.Bundle
import androidx.activity.ComponentActivity

// Renderizar elementos mutables
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Elementos de la app
import com.example.todolist_mobileproject.adapter.ItemAdapter
import com.example.todolist_mobileproject.model.Item

// Eliminar barra de estado celular
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class App : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_layout) // Renderiza el layout app_layout.xml

        // ---------------------------------------------
        // RecyclerView es un elemento xml que permite mostrar una lista de elementos
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager

        // Crea una lista de elementos a renderizar // Reemplazar por API
        val items = mutableListOf(
            Item("Titulo 1", "Descripción 1"),
            Item("Titulo 2", "Descripción 2"),
            Item("Titulo 3", "Descripción 3"),
            Item("Titulo 4", "Descripción 4"),
        )

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
