package com.paa.allsafeproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private val REQUEST_CODE_OK = 1
//    private val REQUEST_CODE_ERROR = 4

    private val TAG:String = "MainActivity"

    var attachedFiles:ArrayList<AttachedFile> = ArrayList()
    var mails:java.util.ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
//        val toast:Toast = Toast.makeText(applicationContext, "main_onCreate", Toast.LENGTH_SHORT)
//        toast.setGravity(Gravity.TOP or Gravity.LEFT,0,0)
//        toast.show()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        Log.d(TAG, "onStart")
//        setListeners()
        super.onStart()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }
    override fun onResume() {
        setListeners()
        Log.d(TAG, "onResume")
        super.onResume()
    }

//    override fun onResume() {
//        fillMailList()
//        fillFileList()
//        super.onResume()
//    }

    private fun fillMailList(receivedMails: java.util.ArrayList<String>?) {
        // TODO(null-проверка полученного листа)
        mails = receivedMails!! // плохо!
        Log.d(TAG, "fillMailList")
        Toast.makeText(applicationContext, "filling mail list", Toast.LENGTH_SHORT).show()
        var ll_mailList:LinearLayout = linearLayout_mailList
        for (i:String in mails) {
            var tw = TextView(this)
            tw.text = i
            ll_mailList.addView(tw)
        }
    }

    private fun setListeners() {
        Log.d(TAG, "setListeners method")
        button_tomails.setOnClickListener {
            val editMailsIntent = Intent(this, MailListActivity::class.java)
            editMailsIntent.putStringArrayListExtra("MainActivity_MAILS", mails)
            startActivityForResult(editMailsIntent,1)
        }
//        button_toFiles.setOnClickListener {
//
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult")
        fillMailList(data?.getStringArrayListExtra("MAILS"))
        super.onActivityResult(requestCode, resultCode, data)
    }
}
