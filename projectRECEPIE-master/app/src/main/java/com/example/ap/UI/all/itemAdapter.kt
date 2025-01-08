@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.ap.UI.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ap.databinding.RecipeCardBinding
import com.bumptech.glide.Glide
import com.example.ap.R
import com.example.ap.data.model.Item
import com.example.ap.utils.showFullscreenImage

// ItemAdapter: מתאם לרשימה שמציג את פריטי המתכונים ב-RecyclerView.
class ItemAdapter(
    private val onEdit: (Item) -> Unit,    // פונקציה לעריכת פריט.
    private val onDelete: (Item) -> Unit,  // פונקציה למחיקת פריט.
    private val onDetails: (Item) -> Unit  // פונקציה להצגת פרטי פריט.
) : androidx.recyclerview.widget.ListAdapter<Item, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {

    // ItemViewHolder: מחזיק את תצוגת הכרטיס עבור כל פריט ברשימה.
    class ItemViewHolder(private val binding: RecipeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // פונקציה שמקשרת את הנתונים של הפריט לתצוגת הכרטיס.
        fun bind(item: Item, onEdit: (Item) -> Unit, onDelete: (Item) -> Unit, onDetails: (Item) -> Unit) {
            val context = binding.root.context
            // הגדרת שם המתכון ושם המחבר.
            binding.foodName.text = item.foodName
            val author = item.authorName.ifEmpty { R.string.Unknown_message }
            binding.authorName.text = context.getString(R.string.author_unknown_card, author)

            // טעינת תמונת המתכון (אם קיימת) באמצעות Glide.
            Glide.with(binding.root.context)
                .load(item.imageUri)
                .into(binding.foodImage)



            // הגדרת פעולה ללחיצה על התמונה להצגת התמונה במסך מלא.
            binding.foodImage.setOnClickListener {
                item.imageUri?.let { uri ->
                    showFullscreenImage(context, uri)
                }
            }




            // הגדרת פעולות ללחצנים: עריכה, מחיקה, והצגת פרטים.
            binding.btnEdit.setOnClickListener {
                onEdit(item) // קריאה לפונקציית העריכה.
            }

            binding.btnDelete.setOnClickListener {
                onDelete(item) // קריאה לפונקציית המחיקה.
            }

            binding.btnViewDetails.setOnClickListener {
                onDetails(item) // קריאה לפונקציית הצגת הפרטים.
            }
        }
    }

    // יצירת ViewHolder עבור פריט חדש.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            RecipeCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    // קישור הנתונים לפריט הנמצא ב-ViewHolder.
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), onEdit, onDelete, onDetails) // שימוש ב-getItem להבטחת רשימה מעודכנת.
    }
}
