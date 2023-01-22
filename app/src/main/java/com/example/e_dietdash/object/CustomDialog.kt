package com.example.e_dietdash.`object`

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.e_dietdash.R

object CustomDialog {
    private var dialog: Dialog? = null

    fun showLoading(activity: Activity){
        val dialogView = activity.layoutInflater.inflate(R.layout.layout_progress, null, false)

        dialog = Dialog(activity)
        dialog?.setCancelable(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setContentView(dialogView)

        dialog?.show()
    }

    fun hideLoading(){
        dialog?.dismiss()
    }
}