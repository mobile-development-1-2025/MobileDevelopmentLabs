package com.example.mymessenger

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
import com.example.mymessenger.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    private val TAG = "ProfileFragment"
    private val viewModel: ProfileViewModel by viewModels()

    private var isUpdatingFromVm = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<EditText>(R.id.etName)
        val etStatus = view.findViewById<EditText>(R.id.etStatus)

        // VM -> UI
        viewModel.name.observe(viewLifecycleOwner) { value ->
            if (etName.text.toString() != value) {
                isUpdatingFromVm = true
                etName.setText(value)
                etName.setSelection(value.length)
                isUpdatingFromVm = false
            }
        }

        viewModel.status.observe(viewLifecycleOwner) { value ->
            if (etStatus.text.toString() != value) {
                isUpdatingFromVm = true
                etStatus.setText(value)
                etStatus.setSelection(value.length)
                isUpdatingFromVm = false
            }
        }

        // UI -> VM
        etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isUpdatingFromVm) return
                viewModel.updateName(s?.toString().orEmpty())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etStatus.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isUpdatingFromVm) return
                viewModel.updateStatus(s?.toString().orEmpty())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }
}
