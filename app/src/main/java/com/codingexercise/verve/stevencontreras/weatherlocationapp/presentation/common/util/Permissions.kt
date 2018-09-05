package com.codingexercise.verve.stevencontreras.weatherlocationapp.presentation.common.util

import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat

class Permissions {
    companion object {
        fun hasPermission(checkPerm: String, context: Context): Boolean {
            return ContextCompat.checkSelfPermission(context, checkPerm) == PackageManager.PERMISSION_GRANTED
        }

        fun collateMissingPermissions(required: ArrayList<String>, context: Context): ArrayList<String> {
            val missingPermissions = ArrayList<String>()
            for (permission in required) {
                if (!hasPermission(permission, context)) {
                    missingPermissions.add(permission)
                }
            }
            return missingPermissions
        }
    }
}