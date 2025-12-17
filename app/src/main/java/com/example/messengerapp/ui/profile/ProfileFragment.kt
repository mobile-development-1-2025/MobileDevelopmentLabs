package com.example.messenger.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.messenger.databinding.FragmentProfileBinding
import com.example.messengerapp.ui.AppViewModel


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val appVM: AppViewModel by activityViewModels()

    private var isEditing = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "ProfileFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appVM.name.observe(viewLifecycleOwner) { binding.nameText.text = it }
        appVM.status.observe(viewLifecycleOwner) { binding.statusText.text = it }

        appVM.name.observe(viewLifecycleOwner) { if (!isEditing) binding.nameEdit.setText(it) }
        appVM.status.observe(viewLifecycleOwner) { if (!isEditing) binding.statusEdit.setText(it) }

        binding.editProfileButton.setOnClickListener {
            if (!isEditing) {
                isEditing = true
                binding.editProfileButton.text = "Сохранить"
                binding.nameEdit.isEnabled = true
                binding.statusEdit.isEnabled = true
            } else {
                isEditing = false
                binding.editProfileButton.text = "Редактировать"
                binding.nameEdit.isEnabled = false
                binding.statusEdit.isEnabled = false

                appVM.updateName(binding.nameEdit.text.toString())
                appVM.updateStatus(binding.statusEdit.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

