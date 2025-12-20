package com.example.messengerlab1.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.messengerlab1.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileScreenViewModel by viewModels()
    private val tagLog = "ProfileFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tagLog, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        Log.d(tagLog, "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tagLog, "onViewCreated")

        binding.tvEmail.text = "dasha@example.com"

        viewModel.nickname.observe(viewLifecycleOwner) { nick ->
            if (binding.inputNickname.text.toString() != nick) {
                binding.inputNickname.setText(nick)
            }
        }

        viewModel.mood.observe(viewLifecycleOwner) { text ->
            if (binding.inputMood.text.toString() != text) {
                binding.inputMood.setText(text)
            }
        }

        binding.inputNickname.addTextChangedListener { editable ->
            viewModel.onNicknameChanged(editable?.toString().orEmpty())
        }

        binding.inputMood.addTextChangedListener { editable ->
            viewModel.onMoodChanged(editable?.toString().orEmpty())
        }
    }

    override fun onStart()  { super.onStart();  Log.d(tagLog, "onStart") }
    override fun onResume() { super.onResume(); Log.d(tagLog, "onResume") }
    override fun onPause()  { super.onPause();  Log.d(tagLog, "onPause") }
    override fun onStop()   { super.onStop();   Log.d(tagLog, "onStop") }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(tagLog, "onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tagLog, "onDestroy")
    }
}
