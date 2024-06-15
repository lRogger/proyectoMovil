package com.example.todolist_mobileproject

import PopupMenuHelper
import android.os.Bundle
import android.widget.Button
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
import com.example.todolist_mobileproject.db.TodoDbHelper

class App : ComponentActivity() {

    private val dbHelper by lazy { TodoDbHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_layout) // Renderiza el layout app_layout.xml

        // ---------------------------------------------
        // RecyclerView es un elemento xml que permite mostrar una lista de elementos
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager

        // Crea una lista de elementos a renderizar // Reemplazar por API
        val items = dbHelper.obtenerTareas()

        // Renderiza la lista de elementos
        val adapter = ItemAdapter(items)
        recyclerView.adapter = adapter


        // Menu de filtros
        val menuButton: Button = findViewById(R.id.filter_button)
        menuButton.setOnClickListener { view ->
            // Actualiza el texto que se muestra en el menu
            PopupMenuHelper.showPopupMenu(this, view) {selectedItemTitle ->
                menuButton.text = selectedItemTitle
            }

            // Aqui se deberia modificar el arreglo de Todos, para dependiendo del filtro
            // mostrar solo los elementos que correspondan
        }


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
