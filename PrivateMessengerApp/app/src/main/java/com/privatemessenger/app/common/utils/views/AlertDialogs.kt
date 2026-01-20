package com.privatemessenger.app.common.utils.views

import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.privatemessenger.app.NavGraphDirections
import com.privatemessenger.app.R
import com.privatemessenger.app.common.utils.system.setVibrateClickListener
import com.privatemessenger.app.databinding.LayoutOkBtnBinding

fun Fragment.showErrorAlert(message: String?, body: (() -> Unit)? = null): AlertDialog =
    showDialog(getString(R.string.failure), message ?: "", body)

fun Fragment.showSuccessAlert(message: String, body: (() -> Unit)? = null): AlertDialog =
    showDialog(getString(R.string.successful), message, body)

fun Fragment.showDialog(title: String, message: String, body: (() -> Unit)? = null): AlertDialog {
    val view = LayoutOkBtnBinding.inflate(LayoutInflater.from(requireContext()))

    val alert = MaterialAlertDialogBuilder(
        requireContext(),
        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Background
    )
        .setTitle(title)
        .setMessage(message)
        .setView(view.root)
        .show()

    view.okBtn.setVibrateClickListener {
        if (message == getString(R.string.need_auth)) {
            findNavController().navigate(NavGraphDirections.startAuthFragmentClear())
        } else if (body != null) {
            body()
        }
        alert.cancel()
    }
    return alert
}