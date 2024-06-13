import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.example.todolist_mobileproject.R

object PopupMenuHelper {
    fun showPopupMenu(context: Context, anchorView: View, onDismiss: (String) -> Unit): String {
        val popupMenu = PopupMenu(context, anchorView)
        val inflater = popupMenu.menuInflater
        var selectedItemTitle = ""

        inflater.inflate(R.menu.filter_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            selectedItemTitle = item.title.toString()
            true
        }

        popupMenu.setOnDismissListener {
            onDismiss(selectedItemTitle)
        }

        popupMenu.show()

        return selectedItemTitle
    }

}
