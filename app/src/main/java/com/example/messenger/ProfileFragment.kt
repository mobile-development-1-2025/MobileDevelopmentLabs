package com.example.messenger

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class ProfileFragment : Fragment() {
    companion object {
        private const val TAG = "ProfileFragment"
    }

    private lateinit var profileViewModel: ProfileViewModel
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

        // Инициализируем ViewModel
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Находим View элементы
        tvUserName = view.findViewById(R.id.tv_user_name)
        tvUserStatus = view.findViewById(R.id.tv_user_status)
        etUserName = view.findViewById(R.id.et_user_name)
        etUserStatus = view.findViewById(R.id.et_user_status)
        btnSave = view.findViewById(R.id.btn_save)

        // Наблюдаем за изменениями в LiveData
        profileViewModel.userName.observe(viewLifecycleOwner) { name ->
            tvUserName.text = name
            etUserName.setText(name)
            Log.d(TAG, "UI обновлен: имя = $name")
        }

        profileViewModel.userStatus.observe(viewLifecycleOwner) { status ->
            tvUserStatus.text = status
            etUserStatus.setText(status)
            Log.d(TAG, "UI обновлен: статус = $status")
        }

        // Обработчик кнопки сохранения
        btnSave.setOnClickListener {
            val newName = etUserName.text.toString()
            val newStatus = etUserStatus.text.toString()

            if (newName.isNotEmpty()) {
                profileViewModel.updateUserName(newName)
            }

            if (newStatus.isNotEmpty()) {
                profileViewModel.updateUserStatus(newStatus)
            }
        }

        return view
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}