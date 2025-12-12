package com.example.messenger

import android.widget.TextView
import androidx.navigation.fragment.findNavController
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val TAG = "LifeCycleLog"

    private lateinit var nameTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var phoneTextView: TextView

    private fun loadProfileData() {
        nameTextView.text = ProfileData.user.name //

        usernameTextView.text = "@${ProfileData.user.username}" //

        phoneTextView.text = ProfileData.user.phoneNumber //
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "ProfileFragment: onAttach() – Присоединение к Activity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ProfileFragment: onCreate()")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "ProfileFragment: onViewCreated() – Разметка создана")

        val editButton = view.findViewById<TextView>(R.id.tv_edit_button)
        nameTextView = view.findViewById(R.id.tv_user_name)
        usernameTextView = view.findViewById(R.id.tv_username)
        phoneTextView = view.findViewById(R.id.tv_phone_number)

        editButton.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }

        loadProfileData()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "ProfileFragment: onStart() – Фрагмент виден")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "ProfileFragment: onResume() – Фрагмент активен")
        loadProfileData()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "ProfileFragment: onPause() – Фрагмент теряет фокус")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "ProfileFragment: onStop() – Фрагмент не виден")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "ProfileFragment: onDestroyView() – Разметка уничтожена")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ProfileFragment: onDestroy() – Фрагмент уничтожен")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "ProfileFragment: onDetach() – Отсоединение от Activity")
    }
}
