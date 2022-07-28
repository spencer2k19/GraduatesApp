package com.example.graduatesapp.ui.utils

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.graduatesapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import okhttp3.RequestBody


fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Context.callPhone(tel:String) {
    val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$tel"))
    startActivity(callIntent)
}



fun Context.showMessage(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun validateField(context: Context, field: String, inputLayout: TextInputLayout): Boolean {
    return if (field.isEmpty()) {
        inputLayout.isErrorEnabled = true
        inputLayout.error = context.getString(R.string.correct_input)
        false
    } else {
        inputLayout.isErrorEnabled = false
        inputLayout.error = null
        true
    }
}

fun View.snackbar(message: String) {

    val snack = Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).also { s ->
        s.setAction("Ok") {
            s.dismiss()
        }
    }
    snack.setActionTextColor(ContextCompat.getColor(context, R.color.white))
    snack.show()

}


fun MaterialButton.setShowProgress(
    showProgress:Boolean?,
    textSource:String?
) {
    iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
    isClickable = showProgress == false
    text = if(showProgress == true) "" else textSource
    icon = if(showProgress == true) {
        CircularProgressDrawable(context).apply {
            setStyle(CircularProgressDrawable.DEFAULT)
            setColorSchemeColors(ContextCompat.getColor(context, R.color.white))
            setArrowDimensions(25f,25f)
            start()
        }
    } else null

    icon?.let {//execute if icon is not null
        icon.callback = object: Drawable.Callback {
            override fun invalidateDrawable(p0: Drawable) {
                this@setShowProgress.invalidate()
            }

            override fun scheduleDrawable(p0: Drawable, p1: Runnable, p2: Long) {

            }

            override fun unscheduleDrawable(p0: Drawable, p1: Runnable) {

            }

        }
    }



}










