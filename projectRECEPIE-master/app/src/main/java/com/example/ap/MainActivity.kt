@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.ap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}


//************************סיכום***************************
/*

data/local_dataBase

    itemDataBase:
        מגדיר את בסיס הנתונים של האפליקציה
        משמש ליצירת טבלה (itemsRecipe) לניהול המתכונים
        מספק גישה ל-DAO (itemsDao) כדי לבצע פעולות על בסיס הנתונים

    itemsDao:
        ממשק גישה לבסיס הנתונים
        מספק פונקציות CRUD (Create, Read, Update, Delete) לניהול פריטים בטבלה

data/model

    Item:
        מחלקת נתונים שמייצגת מתכון
        כוללת שדות כמו שם המתכון, מחבר, מרכיבים, תיאור, ותמונה
        מסומנת כטבלה בבסיס הנתונים (@Entity)

data/repository

    itemRepository:
        אחראי על ניהול הנתונים בין ה-ViewModel לבסיס הנתונים
        מספק ממשק פשוט ל-ViewModel כדי לבצע פעולות כמו הוספה, מחיקה ועדכון של פריטים

UI/add

    add_button_animation:
        מספק אנימציות לכפתור (לדוגמה, הגדלת הכפתור בלחיצה)
        משפר את חוויית המשתמש עם פידבק חזותי

    AddItemFragment:
        מסך שמאפשר למשתמש להוסיף או לערוך מתכון
        כולל טופס למילוי פרטי המתכון ובחירת תמונה

    ValidationUtils:
        מחלקת עזר שמוודאת שכל השדות בטופס הוספת/עריכת מתכון מלאים
        מציגה הודעה אם יש שדות ריקים

UI/all

    AllItemsFragment:
        מציג את כל המתכונים ברשימה
        מאפשר למשתמש לערוך, למחוק, או לראות פרטים על כל מתכון

    itemAdapter:
        מתאם שמחבר בין רשימת המתכונים ל-RecyclerView
        אחראי על עיצוב כרטיסיות המתכונים ותפעול לחצנים (עריכה, מחיקה, פרטים)

    ItemDiffCallback:
        מנהל השוואות בין פריטים ברשימה
        מאפשר ל-RecyclerView לזהות שינויים ולעדכן רק את מה שצריך עם אנימציות

UI/single

    RecipeDetailsFragment:
        מסך שמציג את הפרטים המלאים של מתכון מסוים
        מאפשר למחוק או לערוך את המתכון

    itemViewModel:
        מנהל את כל המידע של המתכונים באפליקציה
        משתמש ב-LiveData כדי לעדכן את הממשק בזמן אמת
        מספק פונקציות הוספה ומחיקה למתכונים

סיכום כללי

האפליקציה בנויה בצורה מודולרית ומבוססת על ארכיטקטורת MVVM:

    Model: אחראי על ניהול הנתונים.
    ViewModel: מקשר בין הנתונים לתצוגה.
    UI: מציג את הנתונים ומאפשר אינטראקציה עם המשתמש.



 */

