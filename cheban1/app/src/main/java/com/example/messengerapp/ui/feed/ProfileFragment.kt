package com.example.messenger.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.messenger.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private val TAG = "ProfileFragment"
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Fragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: View создан")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: View готов к использованию")

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val nameEditText = view.findViewById<EditText>(R.id.profile_name_edit)
        val statusEditText = view.findViewById<EditText>(R.id.profile_status_edit)
        val nameTextView = view.findViewById<TextView>(R.id.profile_name)
        val statusTextView = view.findViewById<TextView>(R.id.profile_status)
        val emailTextView = view.findViewById<TextView>(R.id.profile_email)
        val phoneTextView = view.findViewById<TextView>(R.id.profile_phone)
        val saveButton = view.findViewById<Button>(R.id.save_button)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.profileData.collectLatest { profile ->
                nameTextView.text = profile.name
                statusTextView.text = profile.status
                emailTextView.text = profile.email
                phoneTextView.text = profile.phone
            }
        }

        saveButton.setOnClickListener {
            val newName = nameEditText.text.toString().trim()
            val newStatus = statusEditText.text.toString().trim()

            if (newName.isNotEmpty()) {
                viewModel.updateName(newName)
            }
            if (newStatus.isNotEmpty()) {
                viewModel.updateStatus(newStatus)
            }

            nameEditText.text.clear()
            statusEditText.text.clear()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Fragment запущен")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Fragment возобновлен")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Fragment приостановлен")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Fragment остановлен")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: View уничтожен")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Fragment уничтожен")
    }
}