@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.ap.UI

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ap.data.model.Item
import com.example.ap.data.repository.ItemRepository

//-------------------- זמני --------------------
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
//-------------------- זמני --------------------

class itemViewModel(application: Application): AndroidViewModel(application) {

    // יצירת אובייקט של המחלקה ItemRepository לניהול הנתונים.
    private val repository = ItemRepository(application)

    // משתנה שצופה בשינויים ברשימת הפריטים (LiveData).
    val items: LiveData<List<Item>>? = repository.getItems()

    // פונקציה להוספת פריט חדש.
    fun addItem(item: Item) {
        //--- שימוש זמני בקורוטינה להפעלת הפעולה ברקע ---
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(item)
        }
    }

    // פונקציה למחיקת פריט.
    fun deleteItem(item: Item) {
        repository.deleteItem(item)
    }
}
