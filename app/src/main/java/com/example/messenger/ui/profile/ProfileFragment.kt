package com.example.messenger.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.messenger.R

class ProfileFragment : Fragment() {
    companion object {
        private const val TAG = "ProfileFragment"
    }

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var tvUserName: TextView
    private lateinit var tvUserStatus: TextView
    private lateinit var etUserName: EditText
    private lateinit var etUserStatus: EditText
    private lateinit var btnSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        tvUserName = view.findViewById(R.id.tv_user_name)
        tvUserStatus = view.findViewById(R.id.tv_user_status)
        etUserName = view.findViewById(R.id.et_user_name)
        etUserStatus = view.findViewById(R.id.et_user_status)
        btnSave = view.findViewById(R.id.btn_save)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userName.observe(viewLifecycleOwner) { name ->
            tvUserName.text = name
            etUserName.setText(name)
            Log.d(TAG, "UI обновлен: имя = $name")
        }

        viewModel.userStatus.observe(viewLifecycleOwner) { status ->
            tvUserStatus.text = status
            etUserStatus.setText(status)
            Log.d(TAG, "UI обновлен: статус = $status")
        }

        btnSave.setOnClickListener {
            val newName = etUserName.text.toString()
            val newStatus = etUserStatus.text.toString()

            if (newName.isNotEmpty()) {
                viewModel.updateUserName(newName)
            }

            if (newStatus.isNotEmpty()) {
                viewModel.updateUserStatus(newStatus)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}