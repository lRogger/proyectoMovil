package com.example.todolist_mobileproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_mobileproject.adapter.ItemAdapter
import com.example.todolist_mobileproject.model.Item

class App : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Muestra el layout principal
        setContentView(R.layout.app_layout)


//        // Configura el RecyclerView para mostrar la lista de elementos a renderizar
//        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        // Crea una lista de elementos a renderizar
//        val items = mutableListOf(
//            Item("Titulo 1", "Des`cripción 1"),
//            Item("Titulo 2", "Descripción 2"),
//        )
//
//        // Configura el adaptador para el RecyclerView para renderizar los elementos
//        val adapter = ItemAdapter(items)
//        recyclerView.adapter = adapter
    }
}
