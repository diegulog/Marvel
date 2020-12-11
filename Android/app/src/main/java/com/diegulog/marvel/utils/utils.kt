package com.diegulog.marvel.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.diegulog.marvel.R
import java.security.MessageDigest


const val LOADING_TYPE_ITEM = 1
const val CHARACTERS_TYPE_ITEM = 2

const val BASE_URL = "https://gateway.marvel.com/v1/public/"
const val PUBLIC_KEY = "d86e70f5110515da2b5ea7f48d347f58"
const val PRIVATE_KEY = "1287b638d82c4ad5409077e5eff5333d67d97424"
const val TS = "diegulog"

fun md5(): String  {
    val bytes = MessageDigest.getInstance("MD5").digest("$TS$PRIVATE_KEY$PUBLIC_KEY".toByteArray())
    return bytes.joinToString("") {
         "%02x".format(it)
    }
}

fun View.toVisibility(constraint: Boolean) {
    return if (constraint) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun ImageView.loadGlide(url: String){
    Glide.with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .centerCrop()
        .placeholder(R.drawable.ic_image)
        .into(this)
}