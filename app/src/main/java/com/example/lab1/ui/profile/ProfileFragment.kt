package com.example.lab1.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lab1.R

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var etName: EditText
    private lateinit var etStatus: EditText
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etBio: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Lifecycle", "ProfileFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ProfileFragment", "onCreateView")
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        etName = view.findViewById(R.id.etName)
        etStatus = view.findViewById(R.id.etStatus)
        etUsername = view.findViewById(R.id.etUsername)
        etEmail = view.findViewById(R.id.etEmail)
        etPhone = view.findViewById(R.id.etPhone)
        etBio = view.findViewById(R.id.etBio)

        viewModel.name.observe(viewLifecycleOwner) {
            if (etName.text.toString() != it) etName.setText(it)
        }
        viewModel.status.observe(viewLifecycleOwner) {
            if (etStatus.text.toString() != it) etStatus.setText(it)
        }
        viewModel.username.observe(viewLifecycleOwner) { etUsername.setText(it) }
        viewModel.email.observe(viewLifecycleOwner) { etEmail.setText(it) }
        viewModel.phone.observe(viewLifecycleOwner) { etPhone.setText(it) }
        viewModel.bio.observe(viewLifecycleOwner) {
            if (etBio.text.toString() != it) etBio.setText(it)
        }

        etName.addTextChangedListener(simpleTextWatcher { viewModel.updateName(it) })
        etStatus.addTextChangedListener(simpleTextWatcher { viewModel.updateStatus(it) })
        etBio.addTextChangedListener(simpleTextWatcher { viewModel.updateBio(it) })

        return view
    }

    override fun onStart() {
        super.onStart();
        Log.i("Lifecycle", "ProfileFragment onStart")
    }

    override fun onResume() {
        super.onResume();
        Log.i("Lifecycle", "ProfileFragment onResume")
    }

    override fun onPause() {
        super.onPause();
        Log.i("Lifecycle", "ProfileFragment onPause")
    }

    override fun onStop() {
        super.onStop();
        Log.i("Lifecycle", "ProfileFragment onStop")
    }

    override fun onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "ProfileFragment onDestroy")
    }

    private fun simpleTextWatcher(onChange: (String) -> Unit) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) { onChange(s.toString()) }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}
