package com.example.chattersy.ui.profile

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
import androidx.lifecycle.lifecycleScope
import com.example.chattersy.R
import com.example.chattersy.notifications.ContactsHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment()
{
    companion object
    {
        private const val TAG = "ProfileFragment"
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var userNameEditText: EditText
    private lateinit var userStatusEditText: EditText
    private lateinit var contactsCountText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        userNameEditText = view.findViewById(R.id.userName)
        userStatusEditText = view.findViewById(R.id.userStatus)
        contactsCountText = view.findViewById(R.id.contactsCountText)

        viewModel.userName.observe(viewLifecycleOwner) { name ->
            if (userNameEditText.text.toString() != name)
            {
                userNameEditText.setText(name)
            }
        }

        viewModel.userStatus.observe(viewLifecycleOwner) { status ->
            if (userStatusEditText.text.toString() != status)
            {
                userStatusEditText.setText(status)
            }
        }

        userNameEditText.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?)
            {
                s?.toString()?.let { viewModel.updateUserName(it) }
            }
        })

        userStatusEditText.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?)
            {
                s?.toString()?.let { viewModel.updateUserStatus(it) }
            }
        })

        loadContactsCount()
    }

    override fun onResume() {
        super.onResume()
        loadContactsCount()
    }

    private fun loadContactsCount() {
        if (!::contactsCountText.isInitialized) return
        lifecycleScope.launch {
            val count = withContext(Dispatchers.IO) {
                ContactsHelper.getContactCount(requireContext())
            }
            contactsCountText.text = when (count) {
                null -> getString(R.string.contacts_count_no_access)
                else -> getString(R.string.contacts_count_label, count)
            }
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }
}
