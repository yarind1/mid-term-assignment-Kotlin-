
@file:Suppress("SpellCheckingInspection")

package com.example.ap.data.local_dataBase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ap.data.model.Item

// הקוד מגדיר את בסיס הנתונים. יש טבלה אחת שמתעסקת בפריטים (Items).
// הגרסה של בסיס הנתונים היא 1, ואין צורך לייצא את המידע לסכמה חיצונית.
@Database(entities = arrayOf(Item::class), version = 1, exportSchema = false)
abstract class itemDataBase : RoomDatabase() {

    // הפונקציה מחזירה גישה לטבלה בבסיס הנתונים כדי להוסיף, לעדכן או למחוק פריטים.
    abstract fun ItemsDao(): itemsDao

    companion object {
        // המנגנון מוודא שיהיה רק עותק אחד של בסיס הנתונים בכל האפליקציה (Singleton).
        @Volatile
        private var instance: itemDataBase? = null

        // הפונקציה יוצרת את בסיס הנתונים אם הוא לא קיים, או מחזירה את הקיים.
        fun getDatabase(context: Context) = instance ?: synchronized(lock = this) {
            // השורה יוצרת עותק חדש של בסיס הנתונים במקרה שהוא לא קיים.
            Room.databaseBuilder(
                context.applicationContext,
                itemDataBase::class.java, // כאן מוגדרת המחלקה שתנהל את בסיס הנתונים.
                "items_database" // זה השם של קובץ בסיס הנתונים.
            )
                .allowMainThreadQueries() // השורה מאפשרת לעבוד עם בסיס הנתונים גם מהתהליך הראשי (לא מומלץ באפליקציות גדולות).
                .build() // השורה יוצרת את העותק של בסיס הנתונים.
                .also { instance = it } // השורה שומרת את העותק שיצרנו לשימוש חוזר.
        }
    }
}


