package com.codingexercise.verve.stevencontreras.weatherlocationapp.presentation.common.util

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface

class Alerts {
    companion object {
        fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener, cancelListener: DialogInterface.OnClickListener, activity: Activity) {
            AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }
}