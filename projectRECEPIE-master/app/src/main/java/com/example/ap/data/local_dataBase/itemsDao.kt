@file:Suppress("SpellCheckingInspection")

package com.example.ap.data.local_dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ap.data.model.Item

// המחלקה (interface) itemsDao מוגדרת כממשק גישה לבסיס הנתונים.
// היא מספקת פונקציות להוספה, מחיקה, עדכון ושליפה של פריטים (Items) מבסיס הנתונים.

@Dao
interface itemsDao {

    // הפונקציה מוסיפה פריט חדש לטבלה. אם יש כבר פריט עם אותו מזהה, היא תחליף אותו.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: Item)

    // הפונקציה מוחקת פריט או כמה פריטים מהטבלה לפי המזהים שלהם.
    @Delete
    fun deleteItem(vararg item: Item)

    // הפונקציה מעדכנת פריט קיים בטבלה.
    @Update
    fun updateItem(item: Item)

    // הפונקציה מחזירה את כל הפריטים הקיימים בטבלה, ממוינים לפי שם המתכון (Recipe_name).
    // הפונקציה מחזירה LiveData, כך שהנתונים יתעדכנו אוטומטית בממשק המשתמש בכל שינוי.
    @Query("SELECT * FROM itemsRecipe ORDER BY Recipe_name")
    fun getItems(): LiveData<List<Item>>

    // הפונקציה מחזירה פריט בודד לפי המזהה שלו (id).
    @Query(value = "SELECT * FROM itemsRecipe WHERE id = :id")
    fun getItem(id: Int): Item
}

// companion object: משמש ליצירת מופע יחיד (Singleton) עבור כל מחלקה, כדי לחסוך משאבים.
