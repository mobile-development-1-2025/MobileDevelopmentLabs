package com.example.messenger.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.messenger.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ProfileViewModel by viewModels()
    
    companion object {
        private const val TAG = "ProfileFragment"
    }
    
    private var isUpdatingFromViewModel = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        
        setupObservers()
        setupEditTexts()
    }
    
    private fun setupObservers() {
        viewModel.userName.observe(viewLifecycleOwner, Observer { name ->
            if (!isUpdatingFromViewModel) {
                isUpdatingFromViewModel = true
                binding.editTextUserName.setText(name)
                isUpdatingFromViewModel = false
            }
            Log.d(TAG, "userName changed: $name")
        })
        
        viewModel.userStatus.observe(viewLifecycleOwner, Observer { status ->
            if (!isUpdatingFromViewModel) {
                isUpdatingFromViewModel = true
                binding.editTextUserStatus.setText(status)
                isUpdatingFromViewModel = false
            }
            Log.d(TAG, "userStatus changed: $status")
        })
    }
    
    private fun setupEditTexts() {
        binding.editTextUserName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdatingFromViewModel && s != null) {
                    viewModel.updateUserName(s.toString())
                }
            }
        })
        
        binding.editTextUserStatus.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            
            override fun afterTextChanged(s: Editable?) {
                if (!isUpdatingFromViewModel && s != null) {
                    viewModel.updateUserStatus(s.toString())
                }
            }
        })
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
    
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
        _binding = null
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}


