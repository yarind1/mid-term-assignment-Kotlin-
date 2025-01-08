@file:Suppress("SpellCheckingInspection") // ביטול בדיקת שגיאות כתיב בקובץ

package com.example.ap.UI.add

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ap.databinding.AddRecipeBinding
import androidx.fragment.app.activityViewModels
import com.example.ap.data.model.Item
import com.example.ap.R
import com.example.ap.UI.itemViewModel
import com.example.ap.utils.ValidationUtils
import com.bumptech.glide.Glide
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ap.databinding.UpdateDialogBinding


class AddItemFragment : Fragment() {

    // View Binding for the fragment layout
    private var _binding: AddRecipeBinding? = null
    private val binding get() = _binding!!

    // Variables to store the image URI and the item being edited
    private var imageUri: Uri? = null
    private var itemToEdit: Item? = null

    // ViewModel for managing items
    private val viewModel: itemViewModel by activityViewModels()

    // Image picker launcher
    private val pickImageLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                val fileSize = requireActivity().contentResolver.openAssetFileDescriptor(it, "r")?.length ?: 0
                val maxSize = 2 * 1024 * 1024 // Maximum image size: 2MB.

                if (fileSize > maxSize) {
                    Glide.with(this)
                        .asBitmap()
                        .load(it)
                        .override(500, 500) // Resize to a smaller size
                        .into(binding.foodImagePreview)

                    requireActivity().contentResolver.takePersistableUriPermission(
                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    imageUri = uri
                } else {
                    binding.foodImagePreview.setImageURI(uri)
                    requireActivity().contentResolver.takePersistableUriPermission(
                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    imageUri = uri
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = AddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize itemToEdit from arguments
        itemToEdit = arguments?.getParcelable("item", Item::class.java)
        Log.d("AddItemFragment", "itemToEdit: $itemToEdit")

        // Update UI based on Add or Edit mode
        binding.btnAddFood.text = if (itemToEdit == null) getString(R.string.add_recipe) else getString(R.string.update_recipe)
        binding.headerTitle.text = if (itemToEdit == null) getString(R.string.add_recipe) else getString(R.string.update_recipe)

        // Pre-fill fields if in Edit mode
        itemToEdit?.let { item ->
            binding.foodNameInput.setText(item.foodName)
            binding.authorNameInput.setText(item.authorName)
            binding.foodDescriptionInput.setText(item.description)
            binding.ingredientsDescriptionInput.setText(item.ingredients)
            imageUri = item.imageUri?.let { Uri.parse(it) }
            binding.foodImagePreview.setImageURI(imageUri)
        }

        // Handle Add/Update button click
        binding.btnAddFood.setOnClickListener {
            if (itemToEdit == null) {
                // Add Mode: Directly save the item without showing the dialog
                saveItem()
            } else {
                // Edit Mode: Show the update confirmation dialog
                showCustomDialog()
            }
        }

        // Handle image picker button click
        binding.btnPickImage.setOnClickListener {
            pickImageLauncher.launch(arrayOf("image/*"))
        }

        // Handle back button click
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
        }
    }

    private fun showCustomDialog() {
        // Inflate the custom dialog layout using UpdateDialogBinding
        val binding2 = UpdateDialogBinding.inflate(LayoutInflater.from(requireContext()))

        // Build the dialog
        val dialog = AlertDialog.Builder(requireContext())
            .setView(binding2.root)
            .setCancelable(false) // Prevent dismissal by tapping outside
            .create()

        // Handle "No" button click
        binding2.dialogNoButton.setOnClickListener {
            dialog.dismiss() // Close the dialog
        }

        // Handle "Yes" button click
        binding2.dialogYesButton.setOnClickListener {
            if (ValidationUtils.validateFields(requireContext(), binding, imageUri)) {
                val updatedItem = Item(
                    binding.foodNameInput.text.toString(),
                    binding.authorNameInput.text.toString(),
                    binding.foodDescriptionInput.text.toString(),
                    binding.ingredientsDescriptionInput.text.toString(),
                    imageUri?.toString()
                )

                // Update the ViewModel
                itemToEdit?.let { oldItem ->
                    viewModel.deleteItem(oldItem) // Remove the old item
                }
                viewModel.addItem(updatedItem) // Add the updated item

                Toast.makeText(requireContext(), getString(R.string.item_updated), Toast.LENGTH_SHORT).show()
                dialog.dismiss()

                // Navigate back
                findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
            }
        }

        dialog.show()
    }

    private fun saveItem() {
        if (ValidationUtils.validateFields(requireContext(), binding, imageUri)) {
            val newItem = Item(
                binding.foodNameInput.text.toString(),
                binding.authorNameInput.text.toString(),
                binding.foodDescriptionInput.text.toString(),
                binding.ingredientsDescriptionInput.text.toString(),
                imageUri?.toString()
            )

            // Add the new item to ViewModel
            viewModel.addItem(newItem)

            Toast.makeText(requireContext(), getString(R.string.item_added), Toast.LENGTH_SHORT).show()

            // Navigate back to the list of items
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
