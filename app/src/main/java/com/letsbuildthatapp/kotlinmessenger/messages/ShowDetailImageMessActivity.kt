package com.letsbuildthatapp.kotlinmessenger.messages

import android.app.ActionBar
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.letsbuildthatapp.kotlinmessenger.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_detail_image_mess.*

@Suppress("CAST_NEVER_SUCCEEDS")
class ShowDetailImageMessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_detail_image_mess)
//        val actionBar: ActionBar = actionBar
//        actionBar.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);

        val intentImageMess = intent.getStringExtra("SHOW DETAIL IMAGE")
        Picasso.get().load(intentImageMess).into(imageViewShowImageMess)
    }
}