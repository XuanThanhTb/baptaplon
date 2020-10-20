package com.letsbuildthatapp.kotlinmessenger.models

<<<<<<< HEAD
import android.graphics.Bitmap
import com.letsbuildthatapp.kotlinmessenger.R
import kotlinx.android.synthetic.main.activity_show_detail_image_mess.view.*

=======
<<<<<<< HEAD
class ChatMessage(val id: String, val text: String, val fromId: String, val toId: String, val timestamp: Long) {
    constructor() : this("", "", "", "", -1)
=======
>>>>>>> 91b4e712f84af8775fd3ccfb659670b7ed408d0f
class ChatMessage(val id: String,
                  val text: String,
                  val fromId: String,
                  val toId: String,
                  val image: String,
                  val timestamp: Long) {
    constructor() : this("", "", "", "", "", -1)
>>>>>>> 53a1aca7b3bb1114b765ba07a53d341d34319c6d
}