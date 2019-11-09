package com.apo.template.ui.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["textRes", "textArgs", "isPlural"], requireAll = false)
fun setTextResource(view: TextView, stringRes: Int?, args: Array<Any?>?, isPlural: Boolean?) {
    val stringRes = stringRes ?: 0
    val isPlural = isPlural ?: false
    if (stringRes == 0) {
        view.text = ""
    } else {
        if (args == null) {
            if (isPlural) {
                view.text = view.context.resources.getQuantityString(stringRes, 0)
            } else {
                view.setText(stringRes)
            }
        } else {
            if (isPlural) {
                val getQuantity: Int = args[0] as Int
                view.text = view.context.resources.getQuantityString(stringRes, getQuantity, *args)

            } else {
                view.text = view.context.getString(stringRes, *args)
            }
        }
    }
}