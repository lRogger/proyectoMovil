package com.example.todolist_mobileproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.util.Log

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
        val items = dbHelper.obtenerTareas()

        if (dbHelper.obtenerTareas().isEmpty()) {
            dbHelper.ingresoTarea("Comprar leche", "Ir al supermercado", "Pendiente")
            dbHelper.ingresoTarea("Estudiar Java", "Preparar para examen", "En progreso")
            dbHelper.ingresoTarea("Llamar al médico", "Hacer cita", "Completo")
        }
        Log.d("MainActivity",items.size.toString())


        // Renderiza la lista de elementos
        val adapter = ItemAdapter(items, dbHelper)
        recyclerView.adapter = adapter


        // Menu de filtros
        //        val menuButton: Button = findViewById(R.id.filter_button)
        //        menuButton.setOnClickListener { view ->
        //            // Actualiza el texto que se muestra en el menu
        //            PopupMenuHelper.showPopupMenu(this, view) {selectedItemTitle ->
        //                menuButton.text = selectedItemTitle
        //            }
        //
        //            // Aqui se deberia modificar el arreglo de Todos, para dependiendo del filtro
        //            // mostrar solo los elementos que correspondan
        //        }


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
