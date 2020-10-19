package com.letsbuildthatapp.kotlinmessenger.messages

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.letsbuildthatapp.kotlinmessenger.R

class ShowMenuMessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_menu_mess)
        supportActionBar?.title = ""
    }
}