package com.example.todolist_mobileproject.adapter

import com.example.todolist_mobileproject.model.Item
import com.example.todolist_mobileproject.App

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

import com.example.todolist_mobileproject.R
import com.example.todolist_mobileproject.db.TodoDbHelper

class ItemAdapter(
    private val items: List<Item>,
    private val dbHelper: TodoDbHelper,
    private val app: App
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val title: EditText = view.findViewById(R.id.item_title)
        private val description: EditText = view.findViewById(R.id.item_description)
        private val editButton: Button = view.findViewById(R.id.edit_button)
        private val deleteButton: Button = view.findViewById(R.id.delete_button)
        private val statusCheck: CheckBox = view.findViewById(R.id.item_status_button)
        private val context = itemView.context



        fun bind(item: Item, dbHelper: TodoDbHelper, app: App) {
            title.setText(item.title)
            description.setText(item.description)

            statusCheck.setText( item.state)
            if (statusCheck.text.toString() == context.getString(R.string.filter_opt_done)) {
                statusCheck.setBackgroundResource(R.drawable.status_done_bg)
            }

            // Editar item
            editButton.setOnClickListener {
                val isEnabled = title.isEnabled
                title.isEnabled = !isEnabled
                description.isEnabled = !isEnabled

                if (!isEnabled) {
                    title.requestFocus()
                    editButton.setBackgroundResource(R.drawable.save)

                } else {
                    item.title = title.text.toString()
                    item.description = description.text.toString()
                    item.state = statusCheck.text.toString()
                    dbHelper.actualizarTarea(item)
                    editButton.setBackgroundResource(R.drawable.edit)
                }
            }


            deleteButton.setOnClickListener {
                dbHelper.eliminarTarea(item)
                app.cargarData(dbHelper.obtenerTareas())
            }


            statusCheck.setOnClickListener {

                if (statusCheck.text == context.getString(R.string.filter_opt_pending)) {
                    statusCheck.setText(context.getString(R.string.filter_opt_done))
                    statusCheck.setBackgroundResource(R.drawable.status_done_bg)
                } else {
                    statusCheck.setText(context.getString(R.string.filter_opt_pending))
                    statusCheck.setBackgroundResource(R.drawable.status_pending_bg)

                }
                item.state = statusCheck.text.toString()
                dbHelper.actualizarTarea(item)
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
        holder.bind(currentItem, dbHelper, app)
    }

    override fun getItemCount(): Int = items.size

}