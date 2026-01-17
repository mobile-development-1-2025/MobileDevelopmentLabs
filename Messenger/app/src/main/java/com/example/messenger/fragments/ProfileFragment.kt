package com.example.messenger.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.messenger.R
import com.example.messenger.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    companion object {
        private const val TAG = "ProfileFragment"
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var nameEditText: EditText
    private lateinit var statusEditText: EditText
    private var isUpdatingFromViewModel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView called")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated called")
        
        nameEditText = view.findViewById(R.id.profile_name)
        statusEditText = view.findViewById(R.id.profile_status)
        
        setupObservers()
        setupTextWatchers()
    }

    private fun setupObservers() {
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            if (!isUpdatingFromViewModel && nameEditText.text.toString() != name) {
                isUpdatingFromViewModel = true
                nameEditText.setText(name)
                isUpdatingFromViewModel = false
            }
        }

        viewModel.userStatus.observe(viewLifecycleOwner) { status ->
            if (!isUpdatingFromViewModel && statusEditText.text.toString() != status) {
                isUpdatingFromViewModel = true
                statusEditText.setText(status)
                isUpdatingFromViewModel = false
            }
        }
    }

    private fun setupTextWatchers() {
        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdatingFromViewModel) {
                    viewModel.updateUserName(s.toString())
                }
            }
        })

        statusEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdatingFromViewModel) {
                    viewModel.updateUserStatus(s.toString())
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
}
