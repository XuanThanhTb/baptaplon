package com.letsbuildthatapp.kotlinmessenger.views

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.letsbuildthatapp.kotlinmessenger.R
import com.letsbuildthatapp.kotlinmessenger.messages.ChatLogActivity
import com.letsbuildthatapp.kotlinmessenger.messages.ShowDetailImageMessActivity
import com.letsbuildthatapp.kotlinmessenger.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_image_row.view.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_from_row.view.imageview_chat_from_row
import kotlinx.android.synthetic.main.chat_to_image_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
import kotlin.coroutines.experimental.coroutineContext


class ChatFromItem(val activity: Activity, val text: String, val user: User, val selectedPhotoUri: String) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

        val targetImageView = viewHolder.itemView.imageview_chat_from_row
        Picasso.get().load(user.profileImageUrl).into(targetImageView)
        if (text != "") {
            viewHolder.itemView.textview_from_row.text = text
        } else {
            val targetImageView = viewHolder.itemView.image_from_row
            Log.d("THANH", selectedPhotoUri)
            Picasso.get().load(user.profileImageUrl).into(targetImageView)
            viewHolder.itemView.image_from_row.setOnClickListener {
//                Toast.makeText(activity, "SHOW IMAGE", Toast.LENGTH_SHORT).show()
//                showDialog()
                val intent = Intent(activity, ShowDetailImageMessActivity::class.java)
                activity.startActivity(intent);
            }
        }

    }

    override fun getLayout(): Int {
        return if (text != "") R.layout.chat_from_row else R.layout.chat_from_image_row
    }

    private fun showDialog() {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_show_image_mess)
        val close = dialog.findViewById<ImageView>(R.id.imageViewCloseImage)
        val image = dialog.findViewById<ImageView>(R.id.imageViewShowImageMess)
        if (selectedPhotoUri != null) Picasso.get().load(selectedPhotoUri).into(image) else image.setImageResource(R.drawable.ic_baseline_image_24)

        close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }
}

class ChatToItem(val text: String, val user: User) : Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.textview_to_row.text = text
        val uri = user.profileImageUrl
        val targetImageView = viewHolder.itemView.imageview_chat_to_row
        Picasso.get().load(uri).into(targetImageView)
        // load our user image into the star
    }
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}