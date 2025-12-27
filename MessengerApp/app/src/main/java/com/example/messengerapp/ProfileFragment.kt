package com.example.messengerapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.example.messengerapp.R

class ProfileFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "onCreate ProfileFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("Lifecycle", "onCreateView ProfileFragment")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Lifecycle", "onViewCreated ProfileFragment")

        val nameEditText = view.findViewById<EditText>(R.id.editTextName)
        val statusEditText = view.findViewById<EditText>(R.id.editTextStatus)

        viewModel.userName.observe(viewLifecycleOwner) { name ->
            if (nameEditText.text.toString() != name) {
                nameEditText.setText(name)
            }
        }

        viewModel.userStatus.observe(viewLifecycleOwner) { status ->
            if (statusEditText.text.toString() != status) {
                statusEditText.setText(status)
            }
        }

        nameEditText.doAfterTextChanged { text ->
            viewModel.updateName(text?.toString().orEmpty())
        }

        statusEditText.doAfterTextChanged { text ->
            viewModel.updateStatus(text?.toString().orEmpty())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "onDestroy ProfileFragment")
    }
}
