package com.example.todolist_mobileproject.adapter

import com.example.todolist_mobileproject.model.Item

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import android.widget.Button
import android.widget.EditText
import com.example.todolist_mobileproject.R
import com.example.todolist_mobileproject.db.TodoDbHelper

class ItemAdapter(
    private val items: List<Item>,
    private val dbHelper: TodoDbHelper
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // Aqui se define la estructura de cada item
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val title: EditText = view.findViewById(R.id.item_title)
        private val description: EditText = view.findViewById(R.id.item_description)
        private val editButton: Button = view.findViewById(R.id.edit_button)

        fun bind(item: Item, dbHelper: TodoDbHelper) {
            title.setText(item.title)
            description.setText(item.description)

            // Editar item
            editButton.setOnClickListener {
                val isEnabled = title.isEnabled
                title.isEnabled = !isEnabled
                description.isEnabled = !isEnabled

                if (!isEnabled) {
                    title.requestFocus()
                    editButton.text = "Guardar"
                } else {
                    item.title = title.text.toString()
                    item.description = description.text.toString()
                    dbHelper.actualizarTarea(item)
                    editButton.text = "Editar"
                }
            }
        }
    }

    // Tomamos el layout y lo convertimos a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_component, parent, false)
        return ItemViewHolder(view)
    }

    // Actualizamos los datos del item
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem, dbHelper)
    }

    override fun getItemCount(): Int = items.size
}