package com.letsbuildthatapp.kotlinmessenger.models

<<<<<<< HEAD
class ChatMessage(val id: String, val text: String, val fromId: String, val toId: String, val timestamp: Long) {
    constructor() : this("", "", "", "", -1)
=======
class ChatMessage(val id: String,
                  val text: String,
                  val fromId: String,
                  val toId: String,
                  val image: String,
                  val timestamp: Long) {
    constructor() : this("", "", "", "", "", -1)
>>>>>>> 53a1aca7b3bb1114b765ba07a53d341d34319c6d
}