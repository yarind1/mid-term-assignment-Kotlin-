@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ
package com.example.ap.data.repository

import android.app.Application
import com.example.ap.data.local_dataBase.itemDataBase
import com.example.ap.data.local_dataBase.itemsDao
import com.example.ap.data.model.Item

// מחלקת ItemRepository משמשת כמחבר בין בסיס הנתונים (itemDataBase) לממשק המשתמש.
// היא מספקת פונקציות לגישה למידע בבסיס הנתונים ולעדכון שלו.
class ItemRepository(application: Application) {

    // משתנה שמחזיק את הממשק itemsDao לטיפול בטבלה של הפריטים בבסיס הנתונים.
    private var itemDao: itemsDao?

    // קוד שמופעל בעת יצירת המחלקה. יוצר את בסיס הנתונים ומאתחל את ה-itemDao.
    init {
        // מקבל את מופע בסיס הנתונים מה-context של האפליקציה.
        val db = itemDataBase.getDatabase(application.applicationContext)
        // שומר את ממשק ה-itemsDao שמאפשר לבצע פעולות על הטבלה.
        itemDao = db?.ItemsDao()
    }

    // פונקציה שמחזירה את כל הפריטים מהטבלה בצורה של LiveData.
    fun getItems() = itemDao?.getItems()

    // פונקציה שמוסיפה פריט חדש לטבלה.
    fun addItem(item: Item) {
        itemDao?.addItem(item)
    }

    // פונקציה שמוחקת פריט מהטבלה.
    fun deleteItem(item: Item) {
        itemDao?.deleteItem(item)
    }

    // פונקציה שמחזירה פריט יחיד לפי המזהה שלו.
    fun getItem(id: Int) = itemDao?.getItem(id)
}
