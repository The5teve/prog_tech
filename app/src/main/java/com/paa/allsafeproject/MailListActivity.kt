package com.paa.allsafeproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.LinearLayout
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

    private fun fillMailListView(list: ArrayList<String>?) {
        if (list != null) {
            for (mailString in list) {
//                var lw:LinearLayout = findViewById<LinearLayout>(R.id.linearLayout_mailList)
                var tw:TextView = TextView(applicationContext)
                tw.text = mailString
                LinearLayout_mailListActivity_mailList.addView(tw)
            }
        }
    }

    override fun onResume() {
        setListeners()
        fillMailListView(intent.getStringArrayListExtra("MAILS"))
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
