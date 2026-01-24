package com.example.lab1.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lab1.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val tag = "ProfileFragment"

    // ViewModel, привязанная к этому фрагменту
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate: ProfileFragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        Log.d(tag, "onCreateView: создаём View фрагмента профиля")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated: инициализируем UI профиля")

        // 1. Подписываемся на LiveData из ViewModel
        viewModel.name.observe(viewLifecycleOwner) { name ->
            Log.d(tag, "observe name: $name")
            // Чтобы не зациклиться, ставим текст только если он реально отличается
            if (binding.etProfileName.text.toString() != name) {
                binding.etProfileName.setText(name)
            }
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            Log.d(tag, "observe status: $status")
            if (binding.etProfileStatus.text.toString() != status) {
                binding.etProfileStatus.setText(status)
            }
        }

        // 2. Слушаем изменения текста и прокидываем их в ViewModel
        binding.etProfileName.doOnTextChanged { text, _, _, _ ->
            viewModel.updateName(text?.toString().orEmpty())
        }

        binding.etProfileStatus.doOnTextChanged { text, _, _, _ ->
            viewModel.updateStatus(text?.toString().orEmpty())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(tag, "onDestroyView: View фрагмента профиля уничтожен")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy: ProfileFragment уничтожен")
    }
}
