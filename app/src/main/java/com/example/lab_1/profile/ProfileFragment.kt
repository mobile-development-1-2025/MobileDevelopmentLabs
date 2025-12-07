package com.example.lab_1.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lab_1.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val TAG = "ProfileFragment"

    private val vm: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        Log.i(TAG, "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated")

        vm.name.observe(viewLifecycleOwner) { name ->
            binding.tvName.text = "Name: $name"
        }

        vm.status.observe(viewLifecycleOwner) { status ->
            binding.tvStatus.text = "Status: $status"
        }

        binding.btnEditName.setOnClickListener {
            showEditDialog(
                title = "Edit name",
                initialValue = vm.name.value ?: ""
            ) { newName ->
                vm.setName(newName)
            }
        }

        binding.btnEditStatus.setOnClickListener {
            showEditDialog(
                title = "Edit status",
                initialValue = vm.status.value ?: ""
            ) { newStatus ->
                vm.setStatus(newStatus)
            }
        }
    }

    private fun showEditDialog(
        title: String,
        initialValue: String,
        onSave: (String) -> Unit
    ) {
        val editText = EditText(requireContext())
        editText.setText(initialValue)

        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val text = editText.text.toString().trim()
                if (text.isNotEmpty()) {
                    onSave(text)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        Log.i(TAG, "onDestroyView")
        _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        super.onDestroy()
    }
}
