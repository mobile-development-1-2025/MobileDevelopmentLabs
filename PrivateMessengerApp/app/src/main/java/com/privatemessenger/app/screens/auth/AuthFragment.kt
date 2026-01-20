package com.privatemessenger.app.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.privatemessenger.app.NavGraphDirections
import com.privatemessenger.app.common.utils.views.doOnApplyWindowInsets
import com.privatemessenger.app.common.utils.views.showErrorAlert
import com.privatemessenger.app.common.utils.views.showSuccessAlert
import com.privatemessenger.app.databinding.FragmentAuthBinding
import com.privatemessenger.app.screens.auth.model.AuthUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding

    private val model: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.doOnApplyWindowInsets { view, insets, rect ->
            view.updatePadding(
                top = rect.top + insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                bottom = rect.bottom + if (insets.getInsets(WindowInsetsCompat.Type.ime()).bottom != 0) {
                    insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                } else {
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                }
            )
            insets
        }

        model.refreshTokens()

        viewLifecycleOwner.lifecycleScope.launch {
            model.uiState.collectLatest {
                updateUiState(it)
            }
        }

        binding.actionBtn.setOnClickListener {
            if (!model.uiState.value.isNetworkRequestActive) {
                model.signIn(
                    binding.usernameInput.text.toString(),
                    binding.passwordInput.text.toString()
                )
            }
        }
    }

    private fun updateUiState(state: AuthUiState) {
        if (state.errorMessage.isNotEmpty()) {
            showErrorAlert(state.errorMessage)
            model.clearMessages()
        }

        if (state.infoMessage.isNotEmpty()) {
            showSuccessAlert(state.infoMessage)
            model.clearMessages()
        }

        if (state.isLoggedIn) {
            findNavController().popBackStack()
            findNavController().navigate(NavGraphDirections.startChatListFragment())
        }

        binding.invalidUsernameText.isVisible = state.invalidUsername
        binding.invalidPasswordText.isVisible = state.invalidPassword
    }
}