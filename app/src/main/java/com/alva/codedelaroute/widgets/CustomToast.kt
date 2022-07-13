package com.alva.codedelaroute.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat

import com.alva.codedelaroute.R
import com.bumptech.glide.Glide

object CustomToast {
    @SuppressLint("ResourceAsColor")
    fun showToast(context: Context, message: String, icon: Int, textColor:Int = R.color.white, toastBackgroundColor:Int = R.color.black, isLong: Boolean = false) {
        val li = LayoutInflater.from(context)
        val layouttoast = li.inflate(R.layout.toastcustom, null) as ViewGroup
        val textView = layouttoast.findViewById(R.id.text_toast) as TextView
        val toastIcon=layouttoast.findViewById<ImageView>(R.id.icon_src)
        textView.text = message + ""
        textView.setTextColor(ContextCompat.getColor(context,textColor))

        Glide.with(toastIcon)
            .asDrawable()
            .load(ContextCompat.getDrawable(context, icon))
            .into(toastIcon)

        layouttoast.backgroundTintList = ContextCompat.getColorStateList(context, toastBackgroundColor)


        val scale = context.resources.displayMetrics.density
        val mytoast = Toast(context)
        mytoast.view = layouttoast
        val offsetY = (100 * scale).toInt()
        mytoast.setGravity(Gravity.BOTTOM,0, offsetY)
        mytoast.duration = if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        mytoast.show()
    }
}