package com.privatemessenger.app.screens.chatList

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.privatemessenger.app.NavGraphDirections
import com.privatemessenger.app.R
import com.privatemessenger.app.common.model.UiState
import com.privatemessenger.app.common.utils.views.doOnApplyWindowInsets
import com.privatemessenger.app.common.utils.views.showErrorAlert
import com.privatemessenger.app.databinding.FragmentChatListBinding
import com.privatemessenger.app.screens.chatList.adapter.ChatListAdapter
import com.privatemessenger.app.screens.users.model.UserModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatListFragment : Fragment() {
    private lateinit var binding: FragmentChatListBinding

    private val model: ChatListViewModel by viewModels()

    private val adapter = ChatListAdapter {
        findNavController().popBackStack()
        findNavController().navigate(NavGraphDirections.startChatFragment(it.userModel))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.doOnApplyWindowInsets { view, windowInsetsCompat, rect ->
            view.updatePadding(
                top = rect.top + windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars()).top
            )
            windowInsetsCompat
        }
        binding.addContactFab.doOnApplyWindowInsets { view, insets, rect ->
            binding.addContactFab.updateLayoutParams<ConstraintLayout.LayoutParams> {
                bottomMargin = resources.getDimension(R.dimen._12dp).toInt() + insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                ).bottom
            }
            insets
        }

        binding.addContactFab.setOnClickListener {
            findNavController().navigate(NavGraphDirections.startUsersFragment())
        }

        binding.recycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                model.loadUsers()
                delay(10000)
            }
        }

        model.users.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                }
                is UiState.Error -> {
                    showErrorAlert(it.message)
                }
                is UiState.Success -> {
                    binding.recycler.isVisible = true
                    adapter.submitList(it.data)
                }
            }
        }
    }
}
