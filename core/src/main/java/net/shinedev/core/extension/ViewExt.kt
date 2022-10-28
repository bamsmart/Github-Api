package net.shinedev.core.extension

import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide(stillHadSpace: Boolean = false) {
    visibility = if (stillHadSpace) View.INVISIBLE else View.GONE
}