@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.ap.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// המחלקה Item מייצגת פריט במתכון ושייכת לטבלה בשם "itemsRecipe" בבסיס הנתונים.
@Parcelize
@Entity(tableName = "itemsRecipe")
data class Item(

    // שדה שמייצג את שם המתכון, נשמר בעמודה "Recipe_name".
    @ColumnInfo(name = "Recipe_name")
    val foodName: String,

    // שדה שמייצג את שם מחבר המתכון, נשמר בעמודה "Recipe_authorName".
    @ColumnInfo(name = "Recipe_authorName")
    val authorName: String,

    // שדה שמייצג את תיאור המתכון, נשמר בעמודה "Recipe_description".
    @ColumnInfo(name = "Recipe_description")
    val description: String,

    // שדה שמייצג את רשימת המרכיבים של המתכון, נשמר בעמודה "Recipe_ingredients".
    @ColumnInfo(name = "Recipe_ingredients")
    val ingredients: String,

    // שדה שמייצג את כתובת התמונה של המתכון (אם קיימת), נשמר בעמודה "Recipe_image".
    @ColumnInfo(name = "Recipe_image")
    val imageUri: String? = null // יכול להיות ריק
) : Parcelable { // הופך את המחלקה ל-Parcelable כדי שניתן יהיה להעביר אותה בין Activities/Fragments.

    // מזהה ייחודי עבור כל פריט, מייצג את המפתח הראשי (Primary Key) של הטבלה.
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

