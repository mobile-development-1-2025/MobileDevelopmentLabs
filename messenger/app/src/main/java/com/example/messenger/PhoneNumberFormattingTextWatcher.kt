package com.example.messenger

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneNumberFormattingTextWatcher(private val editText: EditText) : TextWatcher {

    private var isFormatting: Boolean = false
    private var isDeleting: Boolean = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        isDeleting = count > after
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable) {
        if (isFormatting || isDeleting) return

        isFormatting = true

        val originalDigits = s.toString().replace(Regex("[^\\d]"), "")

        val countryCode: String
        val mainDigits: String

        if (originalDigits.isNotEmpty()) {
            countryCode = originalDigits.substring(0, minOf(1, originalDigits.length))
            mainDigits = originalDigits.substring(minOf(1, originalDigits.length))
        } else {
            countryCode = ""
            mainDigits = ""
        }

        val formattedString = StringBuilder("+")
        formattedString.append(countryCode)

        val digitsToFormat = mainDigits.take(10)

        if (digitsToFormat.isNotEmpty()) {
            formattedString.append(" ")

            // (XXX)
            if (digitsToFormat.length > 0) {
                formattedString.append("(")
                formattedString.append(digitsToFormat.substring(0, minOf(3, digitsToFormat.length)))
                if (digitsToFormat.length >= 3) {
                    formattedString.append(") ")
                }
            }

            // XXX-XX-XX
            if (digitsToFormat.length > 3) {
                formattedString.append(digitsToFormat.substring(3, minOf(6, digitsToFormat.length)))
            }
            if (digitsToFormat.length > 6) {
                formattedString.append("-")
                formattedString.append(digitsToFormat.substring(6, minOf(8, digitsToFormat.length)))
            }
            if (digitsToFormat.length > 8) {
                formattedString.append("-")
                formattedString.append(digitsToFormat.substring(8, minOf(10, digitsToFormat.length)))
            }
        }

        editText.setText(formattedString.toString())

        if (formattedString.isNotEmpty()) {
            editText.setSelection(formattedString.length)
        }

        isFormatting = false
    }
}