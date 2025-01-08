@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.ap.utils

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.example.ap.R
import com.example.ap.databinding.AddRecipeBinding

// ValidationUtils: מחלקת עזר לבדיקה ולאימות של שדות טופס הוספת/עדכון מתכון.
object ValidationUtils {

    // הפונקציה validateFields:
    // מטרתה לבדוק אם כל השדות בטופס מלאים, כולל תמונה, ולהחזיר תשובה בהתאם.
    fun validateFields(context: Context, binding: AddRecipeBinding, imageUri: Uri?): Boolean {
        // בדיקה אם כל השדות מלאים
        return if (binding.foodNameInput.text?.isNotEmpty() == true &&
            binding.authorNameInput.text?.isNotEmpty() == true &&
            binding.foodDescriptionInput.text?.isNotEmpty() == true &&
            binding.ingredientsDescriptionInput.text?.isNotEmpty() == true &&
            imageUri != null
        ) {
            true // כל השדות מלאים
        } else {
            // הצגת הודעת שגיאה אם יש שדות ריקים
            Toast.makeText(
                context,
                context.getString(R.string.fill_all_fields_message),
                Toast.LENGTH_SHORT
            ).show()
            false // יש שדה ריק או תמונה חסרה
        }
    }
}

