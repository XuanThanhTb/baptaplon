package com.letsbuildthatapp.kotlinmessenger.models

import android.graphics.Bitmap
import com.letsbuildthatapp.kotlinmessenger.R
import kotlinx.android.synthetic.main.activity_show_detail_image_mess.view.*

class ChatMessage(val id: String,
                  val text: String,
                  val fromId: String,
                  val toId: String,
                  val image: String,
                  val timestamp: Long) {
    constructor() : this("", "", "", "", "", -1)
}