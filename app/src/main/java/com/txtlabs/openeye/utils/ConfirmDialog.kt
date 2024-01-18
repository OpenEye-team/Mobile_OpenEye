package com.txtlabs.openeye.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.txtlabs.openeye.R
import com.txtlabs.openeye.databinding.AlertDialogConfirmationBinding


class ConfirmDialog() : DialogFragment(R.layout.alert_dialog_confirmation) {

    private val binding: AlertDialogConfirmationBinding by viewBinding()
    var title = ""
    var description = ""
    var positiveText = ""
    var negativeText = ""
    var positiveCallback : () -> Unit  = { }
    var negativeCallback : () -> Unit = { }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (title.isNotEmpty()) {
            binding.textView42.text = title
        }

        if (description.isNotEmpty()) {
            binding.textView45.text = description
        }

        if (positiveText.isNotEmpty()) {
            binding.btnNext.text = positiveText
        }

        if (negativeText.isNotEmpty()) {
            binding.btnCancel.text = negativeText
        }

        binding.btnNext.setOnClickListener {
            positiveCallback()
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            negativeCallback()
            dismiss()
        }
    }

}