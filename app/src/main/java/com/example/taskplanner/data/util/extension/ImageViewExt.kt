package com.example.taskplanner.data.util.extension

import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.loadImage(url:String){
    Glide.with(this.context).load(url).into(this)
}