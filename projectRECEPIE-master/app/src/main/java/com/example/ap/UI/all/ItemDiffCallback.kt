@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.ap.UI.all

import androidx.recyclerview.widget.DiffUtil
import com.example.ap.data.model.Item


//*******************************************
//בשביל ליצור אנימציה של מחיקת כרטיסיות
//*******************************************


// ItemDiffCallback: מחלקה שמספקת מנגנון להשוואה בין פריטים ברשימה (RecyclerView).
// המטרה היא לזהות שינויים ברשימה בצורה יעילה וליצור אנימציות מתאימות (כמו מחיקה, הוספה או שינוי).
class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {

    // פונקציה שבודקת אם שני פריטים הם אותם פריטים לפי ה-ID שלהם.
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id // השוואת מזהים ייחודיים (ID).
    }

    // פונקציה שבודקת אם התוכן של שני פריטים זהה לחלוטין.
    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem // השוואה מלאה בין שני הפריטים.
    }
}

