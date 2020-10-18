package com.letsbuildthatapp.kotlinmessenger.messages

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.letsbuildthatapp.kotlinmessenger.R
import com.letsbuildthatapp.kotlinmessenger.models.ChatMessage
import com.letsbuildthatapp.kotlinmessenger.models.User
import com.letsbuildthatapp.kotlinmessenger.views.ChatFromItem
import com.letsbuildthatapp.kotlinmessenger.views.ChatToItem
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLogActivity : AppCompatActivity() {

    companion object {
        val TAG = "ChatLog"
        val GALLERY_INTENT = 2
    }

    val adapter = GroupAdapter<ViewHolder>()
    var toUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        recyclerview_chat_log.adapter = adapter

        toUser = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        supportActionBar?.title = toUser?.username

//    setupDummyData()
        listenForMessages()

        send_button_chat_log.setOnClickListener {
            Log.d(TAG, "Attempt to send message....")
            performSendMessage()
            imageStoreMedia.setImageResource(R.drawable.ic_baseline_image_24)
        }


        imageStoreMedia.setOnClickListener {
            Log.d("RegisterActivity", " Try to show photo select")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, GALLERY_INTENT)
        }
    }

    var selectedPhotoUri: Uri? = null
    var mImageUri = Uri.EMPTY
    var myRef: DatabaseReference? = null
    var myRef2: DatabaseReference? = null

    private fun listenForMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    Log.d(TAG, chatMessage.text)

                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        val currentUser = LatestMessagesActivity.currentUser ?: return
                        adapter.add(ChatFromItem(this@ChatLogActivity, chatMessage.text, currentUser, selectedPhotoUri.toString()))
                    } else {
                        adapter.add(ChatToItem(chatMessage.text, toUser!!))
                    }
                }

                recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)

            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_INTENT && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("RegisterActivity", "photo  was select")

//      val filePath = FirebaseStorage.getInstance().reference.child("images_mess")
//              .child(mImageUri.lastPathSegment)

            selectedPhotoUri = data.data
//            performSendMessage(selectedPhotoUri)

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            imageStoreMedia.setImageBitmap(bitmap)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu_call_and_image, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.call_mess -> {
                Toast.makeText(this, "CALL MESS", Toast.LENGTH_SHORT).show()
            }
            R.id.video_call_mess -> {
                Toast.makeText(this, "VIDEO CALL MESS", Toast.LENGTH_SHORT).show()
            }
            R.id.show_menu_image -> {
                Toast.makeText(this, "SHOW IMAGE MESS", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ShowMenuMessActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun performSendMessage() {
        // how do we actually send a message to firebase...
        val text = edittext_chat_log.text.toString()
        val image = selectedPhotoUri.toString()

        Log.d("THÀNH", selectedPhotoUri.toString())
        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user.uid

        if (fromId == null) return

//    val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()

        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

        val chatMessage = ChatMessage(reference.key!!, text, fromId, toId, image, System.currentTimeMillis() / 1000)

        reference.setValue(chatMessage)
                .addOnSuccessListener {
                    Log.d(TAG, "Saved our chat message: ${reference.key}")
                    edittext_chat_log.text.clear()
                    recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
                }

        toReference.setValue(chatMessage)

        val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
        latestMessageRef.setValue(chatMessage)

        val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")
        latestMessageToRef.setValue(chatMessage)
    }
}
