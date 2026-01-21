package com.example.messengerapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import com.google.android.material.button.MaterialButton
import com.example.messengerapp.viewmodel.ProfileViewModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private val tag = "ProfileFragment"

    private val profileViewModel:  ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate called")
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(tag, "onCreateView called")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated called")
        val usernameTextView = view.findViewById<TextView>(R.id.usernameTextView)
        val aboutTextView = view.findViewById<TextView>(R.id.aboutTextView)
        val editProfileButton = view.findViewById<MaterialButton>(R.id.editProfileButton)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Профиль"
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, requireContext().theme))

        profileViewModel.userName.observe(viewLifecycleOwner) { name ->
            usernameTextView.text = name
        }

        profileViewModel.status.observe(viewLifecycleOwner) { status ->
            aboutTextView.text = status
        }

        editProfileButton.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_edit_profile, null)

            val editName = dialogView.findViewById<EditText>(R.id.editUserName)
            val editStatus = dialogView.findViewById<EditText>(R.id.editStatus)

            editName.setText(profileViewModel.userName.value)
            editStatus.setText(profileViewModel.status.value)

            val alertDialog = AlertDialog.Builder(requireContext())
                .setTitle("Редактирование профиля")
                .setView(dialogView)
                .setPositiveButton("Сохранить") { _, _ ->
                    profileViewModel.userName.value = editName.text.toString()
                    profileViewModel.status.value = editStatus.text.toString()
                }
                .setNegativeButton("Отмена", null)
                .create()

            editName.requestFocus()
            alertDialog.setOnShowListener {
                val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                imm.showSoftInput(editName, android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT)
            }

            alertDialog.show()

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.parseColor("#8780B3"))
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(Color.parseColor("#8780B3"))
        }


    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(tag, "onDestroyView called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy called")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
