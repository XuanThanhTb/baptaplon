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
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
import android.view.View
import android.widget.ImageView
>>>>>>> 53a1aca7b3bb1114b765ba07a53d341d34319c6d
>>>>>>> 91b4e712f84af8775fd3ccfb659670b7ed408d0f
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.letsbuildthatapp.kotlinmessenger.R
import com.letsbuildthatapp.kotlinmessenger.models.ChatMessage
import com.letsbuildthatapp.kotlinmessenger.models.ImageMess
import com.letsbuildthatapp.kotlinmessenger.models.User
import com.letsbuildthatapp.kotlinmessenger.registerlogin.RegisterActivity
import com.letsbuildthatapp.kotlinmessenger.views.ChatFromItem
import com.letsbuildthatapp.kotlinmessenger.views.ChatToItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
>>>>>>> 91b4e712f84af8775fd3ccfb659670b7ed408d0f
import java.util.*
>>>>>>> 53a1aca7b3bb1114b765ba07a53d341d34319c6d

class ChatLogActivity : AppCompatActivity() {

    companion object {
        val TAG = "ChatLog"
        val GALLERY_INTENT = 2
    }

<<<<<<< HEAD
    val adapter = GroupAdapter<ViewHolder>()
    var toUser: User? = null

    var selectedPhotoUri: Uri? = null
    var mImageUri = Uri.EMPTY
    var myRef: DatabaseReference? = null
    var myRef2: DatabaseReference? = null

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
        }


//        imageStoreMedia.setOnClickListener {
//            Log.d("RegisterActivity", " Try to show photo select")
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//            intent.setAction(Intent.ACTION_GET_CONTENT)
//            startActivityForResult(intent, GALLERY_INTENT)
//        }
    }

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
                        adapter.add(ChatFromItem(chatMessage.text, currentUser))
=======
    var selectedPhotoUri: Uri? = null


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
            if (edittext_chat_log.text.toString() == "") uploadImageToFirebaseStorage() else performSendMessage(selectedPhotoUri.toString())

        }


        imageStoreMedia.setOnClickListener {
            Log.d("RegisterActivity", " Try to show photo select")
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, GALLERY_INTENT)
        }
    }


    private fun listenForMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                val imageMess = p0.getValue(ImageMess::class.java)
                if (chatMessage != null) {
                    Log.d(TAG, chatMessage.text)

                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        val currentUser = LatestMessagesActivity.currentUser ?: return
                        adapter.add(ChatFromItem(this@ChatLogActivity, chatMessage.text, currentUser, chatMessage.image))
>>>>>>> 53a1aca7b3bb1114b765ba07a53d341d34319c6d
                    } else {
                        adapter.add(ChatToItem(this@ChatLogActivity, chatMessage.text, toUser!!, chatMessage.image))
                    }
                }

                if (imageMess != null) {
                    Log.d(TAG, imageMess.image)
//                    if ()
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

<<<<<<< HEAD

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_INTENT && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("RegisterActivity", "photo  was select")

            val filePath = FirebaseStorage.getInstance().reference.child("images_mess")
                    .child(mImageUri.lastPathSegment)
            selectedPhotoUri = data.data

//            filePath.putFile(mImageUri).addOnSuccessListener { taskSnapshot ->
//                val downloadUri: Uri = taskSnapshot.uploadSessionUri()
//                val map: ArrayMap<String, String> = ArrayMap()
//                map.put("message", downloadUri.toString())
//                map.put("sender", SignIn.LoggedIn_User_Email)
//                myRef?.push()?.setValue(map)
//                myRef2?.push()?.setValue(map)
//            }

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            imageStoreMedia.setImageBitmap(bitmap)
//            select_photo_button.alpha = 0f
//            val bitmapDrawable = BitmapDrawable(bitmap)
//            select_photo_button.setBackgroundDrawable(bitmapDrawable)
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

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user.uid

        if (fromId == null) return

//    val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()

        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

        val chatMessage = ChatMessage(reference.key!!, text, fromId, toId, System.currentTimeMillis() / 1000)

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
=======
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_INTENT && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("RegisterActivity", "photo  was select")
            selectedPhotoUri = data.data
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


    private fun performSendMessage(profileImageUrl: String) {
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

        val chatMessage = ChatMessage(reference.key!!, text, fromId, toId, profileImageUrl, System.currentTimeMillis() / 1000)

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

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser?.uid
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/imagesmess/$filename")

        ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    Log.d(RegisterActivity.TAG, "Successfully uploaded image: ${it.metadata?.path}")

                    ref.downloadUrl.addOnSuccessListener {
                        Log.d(RegisterActivity.TAG, "File Location: $it")
                        performSendMessage(it.toString())
//                        saveUserToFirebaseDatabase(it.toString())
                    }
                }
                .addOnFailureListener {
                    Log.d(RegisterActivity.TAG, "Failed to upload image to storage: ${it.message}")
                }
    }


    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser?.uid


        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId\"")

//        val user = User(uid, username_edittext_register.text.toString(), profileImageUrl)
        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()

        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()
        val chatMessage = ChatMessage(reference.key!!, "", fromId!!, toId!!, profileImageUrl, System.currentTimeMillis() / 1000)
        ref.setValue(chatMessage)
                .addOnSuccessListener {
                    Log.d(RegisterActivity.TAG, "Finally we saved the user to Firebase Database")

//                    val intent = Intent(this, LatestMessagesActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(intent)

                }
                .addOnFailureListener {
                    Log.d(RegisterActivity.TAG, "Failed to set value to database: ${it.message}")
                }
    }

    private fun saveImageoFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val fromId = FirebaseAuth.getInstance().uid
        val toId = toUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/images/$toId/$fromId")

//        val user = User(uid, username_edittext_register.text.toString(), profileImageUrl)
        val imageMess = ImageMess(profileImageUrl)

        ref.setValue(imageMess)
                .addOnSuccessListener {
                    Log.d(RegisterActivity.TAG, "Finally we saved the user to Firebase Database")

//                    val intent = Intent(this, LatestMessagesActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    startActivity(intent)

                }
                .addOnFailureListener {
                    Log.d(RegisterActivity.TAG, "Failed to set value to database: ${it.message}")
                }
    }

>>>>>>> 53a1aca7b3bb1114b765ba07a53d341d34319c6d
}
