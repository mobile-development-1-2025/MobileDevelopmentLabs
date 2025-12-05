package com.example.messenger_semester_7

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.messenger_semester_7.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileSettingsViewModel by activityViewModels()
    private var editingField: EditingField? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ProfileFragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ProfileFragment", "onViewCreated")

        viewModel.name.observe(viewLifecycleOwner) { name ->
            val text = name ?: ""
            binding.currentNameValue.text = text
            if (editingField != EditingField.NAME && binding.nameInput.text.toString() != text) {
                binding.nameInput.setText(text)
            }
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            val text = status ?: ""
            binding.currentStatusValue.text = text
            if (editingField != EditingField.STATUS && binding.statusInput.text.toString() != text) {
                binding.statusInput.setText(text)
            }
        }

        binding.nameEditButton.setOnClickListener {
            startEditing(EditingField.NAME)
        }

        binding.statusEditButton.setOnClickListener {
            startEditing(EditingField.STATUS)
        }

        binding.saveButton.setOnClickListener {
            val nameText = if (editingField == EditingField.NAME) {
                binding.nameInput.text?.toString().orEmpty()
            } else {
                binding.currentNameValue.text?.toString().orEmpty()
            }
            val statusText = if (editingField == EditingField.STATUS) {
                binding.statusInput.text?.toString().orEmpty()
            } else {
                binding.currentStatusValue.text?.toString().orEmpty()
            }
            viewModel.saveProfile(nameText, statusText)
            stopEditing()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("ProfileFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ProfileFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ProfileFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ProfileFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        editingField = null
        _binding = null
        Log.d("ProfileFragment", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ProfileFragment", "onDestroy")
    }

    private fun startEditing(field: EditingField) {
        if (editingField == field) {
            when (field) {
                EditingField.NAME -> binding.nameInput.requestFocus()
                EditingField.STATUS -> binding.statusInput.requestFocus()
            }
            return
        }
        editingField = field
        binding.saveButton.visibility = View.VISIBLE
        val nameText = binding.currentNameValue.text?.toString().orEmpty()
        val statusText = binding.currentStatusValue.text?.toString().orEmpty()
        binding.nameInput.setText(nameText)
        binding.statusInput.setText(statusText)
        when (field) {
            EditingField.NAME -> {
                binding.currentNameValue.visibility = View.GONE
                binding.nameInput.visibility = View.VISIBLE
                binding.currentStatusValue.visibility = View.VISIBLE
                binding.statusInput.visibility = View.GONE
                binding.nameInput.requestFocus()
            }
            EditingField.STATUS -> {
                binding.currentStatusValue.visibility = View.GONE
                binding.statusInput.visibility = View.VISIBLE
                binding.currentNameValue.visibility = View.VISIBLE
                binding.nameInput.visibility = View.GONE
                binding.statusInput.requestFocus()
            }
        }
    }

    private fun stopEditing() {
        if (editingField == null) {
            return
        }
        editingField = null
        binding.currentNameValue.visibility = View.VISIBLE
        binding.currentStatusValue.visibility = View.VISIBLE
        binding.nameInput.visibility = View.GONE
        binding.statusInput.visibility = View.GONE
        binding.saveButton.visibility = View.GONE
    }

    private enum class EditingField {
        NAME,
        STATUS
    }
}
