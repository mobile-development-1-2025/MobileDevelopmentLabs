package com.example.messenger.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messenger.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val TAG = "ProfileFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ProfileFragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ProfileFragment создание View")
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ProfileFragment View создан")
        
        // Устанавливаем данные профиля
        binding.apply {
            tvUserName.text = "Дмитриев Андрей"
            tvUserEmail.text = "andrey.spb521@gmail.com"
            tvUserPhone.text = "+7 (999) 988-98-89"
            tvUserBio.text = "Android разработчик. Люблю создавать крутые приложения!"
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ProfileFragment стартовал")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ProfileFragment возобновлен")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ProfileFragment приостановлен")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ProfileFragment остановлен")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ProfileFragment View уничтожен")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ProfileFragment уничтожен")
    }
}
