package com.example.todolist_mobileproject

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup

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

        val searchBar: EditText = findViewById(R.id.input_search_bar)

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Código a ejecutar antes de que el texto cambie
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Código a ejecutar mientras el texto está cambiando
            }

            override fun afterTextChanged(s: Editable?) {
                val searchFilter = items.filter { item ->
                    s.toString().lowercase() in item.title.lowercase()
                }

                val idChecked = filterGroup.checkedRadioButtonId
                val filter = filterGroup.findViewById<RadioButton>(idChecked).text

                val applyFilter = searchFilter.filter { item ->
                    item.state === filter.toString()
                }

                cargarData(searchFilter)
            }
        })

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

     fun cargarData(items: List<Item>){
        val recyclerView: RecyclerView = findViewById(R.id.list_recycler_view)
        val linearLayoutManager = LinearLayoutManager(this)
        val adapter = ItemAdapter(items, dbHelper, this)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    private fun agregarTarea(){
        dbHelper.ingresoTarea("",
            "",
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
