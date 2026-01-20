package com.privatemessenger.app.screens.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.privatemessenger.app.NavGraphDirections
import com.privatemessenger.app.common.model.UiState
import com.privatemessenger.app.common.utils.views.doOnApplyWindowInsets
import com.privatemessenger.app.common.utils.views.showErrorAlert
import com.privatemessenger.app.databinding.FragmentUsersBinding
import com.privatemessenger.app.screens.users.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment() {
    private lateinit var binding: FragmentUsersBinding

    private val model: UsersViewModel by viewModels()

    private val adapter = UsersAdapter {
        findNavController().popBackStack()
        findNavController().navigate(NavGraphDirections.startChatFragment(it))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.doOnApplyWindowInsets { view, insets, rect ->
            view.updatePadding(
                top = rect.top + insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
            )
            insets
        }

        binding.recycler.doOnApplyWindowInsets { view, insets, rect ->
            view.updatePadding(
                bottom = rect.bottom + insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom,
            )
            insets
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        model.loadUsers()

        binding.recycler.adapter = adapter
        binding.recycler.isVisible = false
        binding.indicator.isVisible = false

        model.users.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    binding.indicator.isVisible = true
                    binding.recycler.isVisible = false
                }

                is UiState.Error -> {
                    binding.indicator.isVisible = false
                    showErrorAlert(it.message)
                }

                is UiState.Success -> {
                    binding.indicator.isVisible = false
                    binding.recycler.isVisible = true

                    adapter.submitList(it.data)
                }
            }
        }
    }
}