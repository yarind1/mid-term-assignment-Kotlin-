@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.ap.UI.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ap.R
import com.example.ap.UI.add.add_button_animation
import com.example.ap.UI.itemViewModel
import com.example.ap.databinding.AllRecipeListBinding
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ap.data.model.Item
import com.example.ap.databinding.DeleteDialogBinding

// AllItemsFragment: מסך שמציג את כל הפריטים (רשימת המתכונים).
class AllItemsFragment : Fragment() {

    // משתנה שמחזיק את העיצוב של המסך.
    private var _binding: AllRecipeListBinding? = null
    private val binding get() = _binding!!

    // ViewModel לשמירה ולעדכון המידע של הפריטים (LiveData).
    private val viewModel: itemViewModel by activityViewModels()

    // יצירת עיצוב המסך (Binding).
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // אינפלציה של ה-Layout וקישור ה-Binding.
        _binding = AllRecipeListBinding.inflate(inflater, container, false)

        // לחיצה על כפתור התפריט (menuIcon) שמבצעת אנימציה ומעבירה למסך הוספת מתכון.
        binding.menuIcon.setOnClickListener { view ->
            add_button_animation.scaleView(view) {
                findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment)
            }
        }

        return binding.root
    }

    // לוגיקה שמופעלת אחרי שהמסך נוצר.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // יצירת מתאם (Adapter) לרשימה עם פעולות עריכה, מחיקה וצפייה בפרטים.
        val adapter = ItemAdapter(
            onEdit = { item ->
                // מעבר למסך העריכה עם הפריט שנבחר.
                val bundle = Bundle().apply { putParcelable("item", item) }
                findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment, bundle)
            },
            onDelete = { item ->
                // מחיקת פריט מהרשימה.
                showDeleteDialog(item) // Show delete dialog on delete
            },
            onDetails = { item ->
                // מעבר למסך פרטי הפריט.
                val bundle = Bundle().apply { putParcelable("item", item) }
                findNavController().navigate(R.id.action_allItemsFragment_to_recipeDetailsFragment2, bundle)
            }
        )

        // הגדרת המתאם והעיצוב לרשימה (RecyclerView).
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1) // עיצוב טור אחד.

        // צפייה ב-LiveData של הפריטים והעברתם למתאם.
        viewModel.items?.observe(viewLifecycleOwner) { itemList ->
            adapter.submitList(itemList) // מעביר את הרשימה ל-Adapter.
        }
    }

    private fun showDeleteDialog(item: Item) {
        // Inflate the custom dialog layout
        val binding2 = DeleteDialogBinding.inflate(LayoutInflater.from(requireContext()))

        // Build the dialog
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding2.root)
            .setCancelable(false) // Prevent dismissal by tapping outside
            .create()

        // Handle "No" button click
        binding2.dialogNoButton.setOnClickListener {
            dialog.dismiss() // Close the dialog
        }

        // Handle "Yes" button click
        binding2.dialogYesButton.setOnClickListener {
            // Perform delete action
            viewModel.deleteItem(item) // Delete the item
            Toast.makeText(requireContext(), getString(R.string.item_deleted), Toast.LENGTH_SHORT).show()
            dialog.dismiss() // Close the dialog
        }

        dialog.show()
    }

    // ניקוי ה-Binding כדי למנוע דליפות זיכרון.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

