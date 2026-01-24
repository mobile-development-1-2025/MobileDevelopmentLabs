package com.example.messenger.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.messenger.R

enum class UserStatus(val displayName: String) {
    ONLINE("В сети"),
    RECENTLY("Был недавно"),
    OFFLINE("Не в сети")
}

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var nameEditText: EditText
    private lateinit var statusSpinner: Spinner
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText = view.findViewById(R.id.profile_name_edit)
        statusSpinner = view.findViewById(R.id.profile_status_spinner)
        saveButton = view.findViewById(R.id.profile_save_button)

        val statuses = UserStatus.values().map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, statuses)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        statusSpinner.adapter = adapter

        viewModel.userName.observe(viewLifecycleOwner, Observer { name ->
            if (nameEditText.text.toString() != name) {
                nameEditText.setText(name)
            }
        })

        viewModel.userStatus.observe(viewLifecycleOwner, Observer { status ->
            val index = statuses.indexOf(status)
            if (index >= 0) statusSpinner.setSelection(index)
        })

        saveButton.setOnClickListener {
            viewModel.updateUserName(nameEditText.text.toString())
            val selectedStatus = statuses[statusSpinner.selectedItemPosition]
            viewModel.updateUserStatus(selectedStatus)
        }
    }
}
