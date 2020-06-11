package com.bau.bemoss.helper

import android.app.Activity
import android.graphics.Color
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.bau.bemoss.R

class MyProgressDialog {

    // Custom Loading Indicator
    fun getInstance(activity: Activity?): ACProgressFlower? {
        val progressDialog = ACProgressFlower.Builder(activity)
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(Color.WHITE)
            .fadeColor(Color.DKGRAY).build()
        progressDialog.setCancelable(false)
        return progressDialog
    }
}

