package com.example.playcontacts.helpers

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.playcontacts.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object ModalHelper {

    fun showToast(context: Context, text: String, long: Boolean = false) {
        Toast.makeText(context, text, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }

    /**
     *  @param context needs to be this@Activity or applicationContext or requireContext()
     *  @return AlertDialog with String default 'Por favor, espera un momento...'
     */
    fun showLoadingDialog(context: Context): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(context)
        val inflater = LayoutInflater.from(context).inflate(R.layout.loading_dialog_helper, null)
        dialog.setView(inflater)
        dialog.setCancelable(false)
        dialog.setCancelable(false)
        return dialog.show()
    }

    /**
     *  @param context needs to be this@Activity or applicationContext or requireContext()
     *  @param title String
     *  @return AlertDialog with custom String
     */
    fun showLoadingDialog(context: Context, title: String): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(context)
        val inflater = LayoutInflater.from(context).inflate(R.layout.loading_dialog_helper, null)
        val titleView = inflater.findViewById<TextView>(R.id.tv_loading_dialog_title)
        titleView.text = title
        dialog.setView(inflater)
        dialog.setCancelable(false)
        return dialog.show()
    }

    fun dismissDialog(dialog: Dialog) {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
}