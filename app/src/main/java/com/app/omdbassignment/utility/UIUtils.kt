package com.app.omdbassignment.utility

import android.app.ProgressDialog
import android.content.Context
import com.app.omdbassignment.R

object UIUtils {
    private var progressDialog: ProgressDialog? = null
    fun showProgressDialog(context: Context) {
        try {
            progressDialog?.dismiss()
        } catch (e: Exception) {

        }
        progressDialog =
            ProgressDialog.show(context, "", context.getString(R.string.wait),
                true, false)

    }

    fun hideProgressDialog() {
        try {
            progressDialog?.dismiss()
        } catch (e: Exception) {

        }
    }

}