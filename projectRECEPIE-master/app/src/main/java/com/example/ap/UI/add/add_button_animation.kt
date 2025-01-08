@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.ap.UI.add

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

// add_button_animation: מחלקה שמבצעת אנימציות של כפתור בעת לחיצה.
object add_button_animation {

    // הפונקציה scaleView: מבצעת אנימציה של שינוי גודל על כפתור.
    fun scaleView(view: View, duration: Long = 300, onEnd: (() -> Unit)? = null) {
        // יצירת אנימציה לשינוי גודל בציר X
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.2f, 1f)

        // יצירת אנימציה לשינוי גודל בציר Y
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.2f, 1f)

        // הגדרת משך האנימציה ואינטרפולציה (תנועה חלקה)
        scaleX.duration = duration
        scaleY.duration = duration
        scaleX.interpolator = AccelerateDecelerateInterpolator()
        scaleY.interpolator = AccelerateDecelerateInterpolator()

        // הפעלת שתי האנימציות יחד
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(scaleX, scaleY)

        // Listener לסיום האנימציה
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {} // לא מבצע כלום בתחילת האנימציה
            override fun onAnimationEnd(animation: Animator) {
                onEnd?.invoke() // קורא ל-Callback אם הועבר (אופציונלי)
            }
            override fun onAnimationCancel(animation: Animator) {} // לא מבצע כלום בביטול האנימציה
            override fun onAnimationRepeat(animation: Animator) {} // לא מבצע כלום בחזרת האנימציה
        })

        // התחלת האנימציה
        animatorSet.start()
    }
}

