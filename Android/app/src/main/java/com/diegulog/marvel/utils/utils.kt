package com.diegulog.marvel.utils

import android.view.View


const val LOADING_TYPE_ITEM = 1
const val CHARACTERS_TYPE_ITEM = 2

fun View.toVisibility(constraint: Boolean) {
    return if (constraint) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}
