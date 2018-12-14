package com.tanyayuferova.muteme.ui

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View

/**
 * Author: Tanya Yuferova
 * Date: 12/1/2018
 */
fun View.makeShortSnackbar(@StringRes resId: Int): Snackbar {
    return Snackbar.make(this, resId, Snackbar.LENGTH_SHORT)
}

fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Activity.isShouldShowRequestPermissionRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}

fun View.setGone(gone: Boolean = true) {
    visibility = if(gone) View.GONE else View.VISIBLE
}

fun Context.getDecodedResource(@DrawableRes id: Int): Bitmap {
    return BitmapFactory.decodeResource(this.resources, id)
}