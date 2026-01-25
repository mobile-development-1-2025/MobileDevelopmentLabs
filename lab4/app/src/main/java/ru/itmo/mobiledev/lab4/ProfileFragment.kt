package ru.itmo.mobiledev.lab4

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        val nameInput = view.findViewById<TextInputEditText>(R.id.profile_name_input)
        val statusInput = view.findViewById<TextInputEditText>(R.id.profile_status_input)

        viewModel.name.observe(viewLifecycleOwner) { name ->
            if (nameInput.text?.toString() != name) {
                nameInput.setText(name)
            }
        }
        viewModel.status.observe(viewLifecycleOwner) { status ->
            if (statusInput.text?.toString() != status) {
                statusInput.setText(status)
            }
        }

        nameInput.doAfterTextChanged { text ->
            viewModel.updateName(text?.toString().orEmpty())
        }
        statusInput.doAfterTextChanged { text ->
            viewModel.updateStatus(text?.toString().orEmpty())
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "ProfileFragment"
    }
}
