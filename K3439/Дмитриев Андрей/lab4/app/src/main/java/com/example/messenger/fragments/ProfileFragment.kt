package com.example.messenger.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.messenger.databinding.FragmentProfileBinding
import com.example.messenger.viewmodel.ProfileViewModel

/**
 * Экран "Профиль" с использованием MVVM архитектуры
 * Данные сохраняются во ViewModel и переживают повороты экрана
 */
class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val TAG = "ProfileFragment"
    
    // ViewModel с использованием делегата viewModels()
    private val viewModel: ProfileViewModel by viewModels()

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
        
        setupObservers()
        setupListeners()
    }
    
    /**
     * Настройка наблюдателей LiveData
     */
    private fun setupObservers() {
        // Наблюдаем за изменением имени пользователя
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            Log.d(TAG, "Observer: Получено новое имя - '$name'")
            if (binding.etUserName.text.toString() != name) {
                binding.etUserName.setText(name)
            }
        }
        
        // Наблюдаем за изменением статуса пользователя
        viewModel.userStatus.observe(viewLifecycleOwner) { status ->
            Log.d(TAG, "Observer: Получен новый статус - '$status'")
            if (binding.etUserStatus.text.toString() != status) {
                binding.etUserStatus.setText(status)
            }
        }
    }
    
    /**
     * Настройка слушателей ввода
     */
    private fun setupListeners() {
        // Слушатель изменения имени
        binding.etUserName.doAfterTextChanged { text ->
            val newName = text?.toString() ?: ""
            if (viewModel.userName.value != newName) {
                viewModel.updateUserName(newName)
            }
        }
        
        // Слушатель изменения статуса
        binding.etUserStatus.doAfterTextChanged { text ->
            val newStatus = text?.toString() ?: ""
            if (viewModel.userStatus.value != newStatus) {
                viewModel.updateUserStatus(newStatus)
            }
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
