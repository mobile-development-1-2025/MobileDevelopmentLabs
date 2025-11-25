package com.example.lab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class ProfileFragment : Fragment() {

    private val viewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameView = view.findViewById<TextView>(R.id.profileName)
        val emailView = view.findViewById<TextView>(R.id.profileEmail)
        val editButton = view.findViewById<Button>(R.id.editProfileButton)

        viewModel.profileState.observe(viewLifecycleOwner) { profile ->
            nameView.text = profile.name
            emailView.text = profile.email
        }

        editButton.setOnClickListener { showEditMenu(it, nameView.text.toString(), emailView.text.toString()) }
    }

    private fun showEditMenu(anchor: View, currentName: String, currentEmail: String) {
        val popup = PopupMenu(requireContext(), anchor)
        popup.menu.add(0, MENU_EDIT_NAME, 0, getString(R.string.menu_edit_name))
        popup.menu.add(0, MENU_EDIT_EMAIL, 1, getString(R.string.menu_edit_email))
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                MENU_EDIT_NAME -> {
                    showEditDialog(
                        title = getString(R.string.menu_edit_name),
                        currentValue = currentName
                    ) { newValue -> viewModel.updateName(newValue) }
                    true
                }

                MENU_EDIT_EMAIL -> {
                    showEditDialog(
                        title = getString(R.string.menu_edit_email),
                        currentValue = currentEmail
                    ) { newValue -> viewModel.updateEmail(newValue) }
                    true
                }

                else -> false
            }
        }
        popup.show()
    }

    private fun showEditDialog(
        title: String,
        currentValue: String,
        onSave: (String) -> Unit
    ) {
        val input = EditText(requireContext()).apply {
            setText(currentValue)
            setSelection(currentValue.length)
        }

        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setView(input)
            .setPositiveButton(R.string.dialog_save) { _, _ ->
                val newValue = input.text.toString()
                if (newValue.isNotBlank()) {
                    onSave(newValue.trim())
                }
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .show()
    }

    companion object {
        private const val MENU_EDIT_NAME = 1
        private const val MENU_EDIT_EMAIL = 2
    }
}
