package com.example.vsemk.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.vsemk.R
import com.google.android.material.textfield.TextInputEditText

class ProfileFragment : Fragment() {

    companion object {
        private const val TAG = "ProfileFragment"
    }

    private val viewModel: ProfileViewModel by viewModels()

    private lateinit var etUserName: TextInputEditText
    private lateinit var etUserStatus: TextInputEditText

    private var isUpdatingFromViewModel = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        etUserName = view.findViewById(R.id.et_user_name)
        etUserStatus = view.findViewById(R.id.et_user_status)

        viewModel.userName.observe(viewLifecycleOwner, Observer { name ->
            if (!isUpdatingFromViewModel && etUserName.text?.toString() != name) {
                isUpdatingFromViewModel = true
                etUserName.setText(name)
                isUpdatingFromViewModel = false
            }
        })

        viewModel.userStatus.observe(viewLifecycleOwner, Observer { status ->
            if (!isUpdatingFromViewModel && etUserStatus.text?.toString() != status) {
                isUpdatingFromViewModel = true
                etUserStatus.setText(status)
                isUpdatingFromViewModel = false
            }
        })

        etUserName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isUpdatingFromViewModel) {
                    viewModel.updateUserName(s?.toString() ?: "")
                }
            }
        })

        etUserStatus.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isUpdatingFromViewModel) {
                    viewModel.updateUserStatus(s?.toString() ?: "")
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }
}