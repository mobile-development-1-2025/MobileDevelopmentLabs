package com.example.messengerapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.messengerapp.R

class ProfileFragment : Fragment() {

    private val TAG = "ProfileFragment"
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
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

        val tvUsername = view.findViewById<TextView>(R.id.tv_username)
        val tvStatus = view.findViewById<TextView>(R.id.tv_status)
        val etUsername = view.findViewById<EditText>(R.id.et_username)
        val etStatus = view.findViewById<EditText>(R.id.et_status)
        val btnSave = view.findViewById<Button>(R.id.btn_save)

        viewModel.username.observe(viewLifecycleOwner) { name ->
            tvUsername.text = name
            view.findViewById<TextView>(R.id.tv_avatar_letter).text = name.first().toString()
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            tvStatus.text = status
        }

        btnSave.setOnClickListener {
            val newName = etUsername.text.toString().trim()
            val newStatus = etStatus.text.toString().trim()
            if (newName.isNotEmpty()) viewModel.updateUsername(newName)
            if (newStatus.isNotEmpty()) viewModel.updateStatus(newStatus)
            etUsername.text.clear()
            etStatus.text.clear()
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart called") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume called") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause called") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop called") }
    override fun onDestroyView() { super.onDestroyView(); Log.d(TAG, "onDestroyView called") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy called") }
}