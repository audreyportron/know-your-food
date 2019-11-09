package com.apo.template.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * make a view gone or visible
 */
@BindingAdapter("visible")
fun setVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}