package com.paa.allsafeproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_mail_list.*

class MailListActivity : AppCompatActivity() {

    private val TAG:String = "MailListActivity"

    var mailList:java.util.ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail_list)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        setListeners()
        super.onResume()
    }

    private fun setListeners() {
        imageButton_mailsList_backButton.setOnClickListener {
            var mainActivityIntent:Intent = Intent()
            mainActivityIntent.putExtra("MAILS", mailList)
            setResult(Activity.RESULT_OK, mainActivityIntent)
            finish()
        }
        button_addMail.setOnClickListener {
            var input:String = et_mailInput.text.toString()
            mailList.add(input)
            Toast.makeText(applicationContext,"${TAG}_Added $input", Toast.LENGTH_SHORT).show()
            var tw_mail: TextView = TextView(applicationContext)
            tw_mail.text = input
            et_mailInput.text.clear()
            LinearLayout_mailListActivity_mailList.addView(tw_mail)
            super.onResume()
        }
    }

    override fun onBackPressed() {
        var mainActivityIntent:Intent = Intent()
        mainActivityIntent.putExtra("MAILS", mailList)
        setResult(Activity.RESULT_OK, mainActivityIntent)
        finish()
    }
}
