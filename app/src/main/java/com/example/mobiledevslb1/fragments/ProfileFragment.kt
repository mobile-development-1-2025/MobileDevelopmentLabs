package com.example.mobiledevslb1.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import com.example.mobiledevslb1.R
import com.example.mobiledevslb1.viewmodels.ProfileViewModel

class ProfileFragment: FragmentLogged() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_layout, container, false)
    }

    private val _viewModel: ProfileViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameEdit = view.findViewById<EditText>(R.id.lblNameValue)
        val ageEdit = view.findViewById<EditText>(R.id.lblAgeValue)

        nameEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                _viewModel.setName(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        ageEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                _viewModel.setAge(s.toString().toIntOrNull())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        _viewModel.name.observe(viewLifecycleOwner) { name ->
            nameEdit.post {
                if (nameEdit.text.toString() != name) {
                    nameEdit.setText(name)
                }
            }
            Log.d("ViewModel", "Name changed")
        }

        _viewModel.age.observe(viewLifecycleOwner) { age ->
            nameEdit.post {
                if (ageEdit.text.toString() != age.toString()) {
                    ageEdit.setText(age.toString())
                }
            }
            Log.d("ViewModel", "Age changed")
        }
    }
}