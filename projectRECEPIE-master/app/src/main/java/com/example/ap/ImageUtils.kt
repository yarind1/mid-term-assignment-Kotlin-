package com.example.ap.utils

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import com.example.ap.databinding.DialogFullscreenImageBinding
import com.example.ap.UI.add.add_button_animation

fun showFullscreenImage(context: Context, imageUri: String) {
    val dialog = Dialog(context)

    val binding = DialogFullscreenImageBinding.inflate(dialog.layoutInflater)
    dialog.setContentView(binding.root)

    dialog.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )


    dialog.window?.setBackgroundDrawableResource(android.R.color.black)

    binding.fullscreenImage.setImageURI(Uri.parse(imageUri))

    binding.closeButton.setOnClickListener { view ->
        add_button_animation.scaleView(view) {
            dialog.dismiss()
        }
    }

    dialog.show()
}
