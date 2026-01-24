package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.viewmodel.ProfileViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var viewModel: ProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]

        val nameEdit = view.findViewById<EditText>(R.id.editName)
        val statusEdit = view.findViewById<EditText>(R.id.editStatus)

        viewModel.name.observe(viewLifecycleOwner) { name ->
            nameEdit.post {
                if (nameEdit.text.toString() != name) {
                    nameEdit.setText(name)
                }
            }
        }
        viewModel.status.observe(viewLifecycleOwner) { status ->
            statusEdit.post {
                if (statusEdit.text.toString() != status) {
                    statusEdit.setText(status)
                }
            }
        }

        nameEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.updateName(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        statusEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.updateStatus(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        Log.i("Lifecycle", "ProfileFragment onViewCreated")
    }

    override fun onDestroyView() {
        super.onDestroyView();
        Log.i("Lifecycle", "ProfileFragment onDestroyView")
    }
}