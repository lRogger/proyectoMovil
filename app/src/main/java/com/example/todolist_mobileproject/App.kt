package com.example.todolist_mobileproject

import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

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
import androidx.core.view.forEach
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
    private lateinit var filterGroup: RadioGroup
    private lateinit var items: List<Item>
    private val dbHelper by lazy { TodoDbHelper(this) }

    private fun initComponents() {
        items = dbHelper.obtenerTareas()
        btnAdd = findViewById(R.id.add_item_button)
        filterGroup = findViewById(R.id.filter_radio_group)
    }

    private fun initListeners() {
        filterItems(items)

        btnAdd.setOnClickListener{
            agregarTarea()
        }
    }

    private fun initUI() {
        eliminarBarra()
        cargarData(items)
    }

    private fun eliminarBarra(){
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cargarData(items: List<Item>){
        val recyclerView: RecyclerView = findViewById(R.id.list_recycler_view)
        val linearLayoutManager = LinearLayoutManager(this)
        val items: List<Item> = dbHelper.obtenerTareas()
        val adapter = ItemAdapter(items, dbHelper)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    private fun agregarTarea(){
        dbHelper.ingresoTarea(getString(R.string.item_input_title_hint),
            getString(R.string.item_input_description_hint),
            getString(R.string.filter_opt_pending))
        cargarData(dbHelper.obtenerTareas())
    }

    private fun filterItems(items: List<Item>) {
        filterGroup.setOnCheckedChangeListener { group, checkedId ->
            group.forEach { it ->
                val filter = it as RadioButton

                if (filter.id == checkedId) {
                    filter.setBackgroundResource(R.drawable.search_input_selected_bg)
                    filter.setTextColor(getColor(R.color.input_selected_text_color))

                    val isAll = filter.text === getString(R.string.filter_opt_all)

                    if (!isAll) {
                        val filteredItems = items.filter { item -> item.state == filter.text }
                        cargarData(filteredItems)
                    } else {
                        cargarData(items)
                    }

                } else {
                    filter.setBackgroundResource(R.drawable.search_input_bg)
                    filter.setTextColor(getColor(R.color.input_text_color))
                }
            }
        }
    }
}
