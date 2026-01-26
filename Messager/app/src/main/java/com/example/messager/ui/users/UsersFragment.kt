package com.example.messager.ui.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.messager.databinding.FragmentUsersBinding

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val usersViewModel =
            ViewModelProvider(this).get(UsersViewModel::class.java)

        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textUsers
        usersViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        Log.d("Users", "onCreateView")
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Users", "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Users", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Users", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Users", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Users", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Users", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("Users", "onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Users", "onDestroy")
    }
}