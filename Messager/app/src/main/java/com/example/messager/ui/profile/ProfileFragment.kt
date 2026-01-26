package com.example.messager.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.messager.databinding.FragmentProfileBinding
import com.example.messager.ui.users.UsersViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("Profile", "onCreateView")

        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val profileName: EditText = binding.tvProfileName
        var isEditableProfile = false
        profileName.isFocusable = isEditableProfile
        profileName.isFocusableInTouchMode = isEditableProfile
        binding.rowEditProfileTest.setOnClickListener {
            isEditableProfile = !isEditableProfile
            profileName.isFocusable = isEditableProfile
            profileName.isFocusableInTouchMode = isEditableProfile

        }
        profileName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isEditableProfile) {
                    profileViewModel.updateText(s.toString())
                }
            }
        })


        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Profile", "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Profile", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Profile", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Profile", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Profile", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Profile", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("Profile", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Profile", "onDestroy")
    }
}