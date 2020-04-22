package com.paa.allsafeproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import com.paa.allsafeproject.adapters.MailListAdapter
import com.paa.allsafeproject.data_structs.AttachedMail
import kotlinx.android.synthetic.main.activity_file_list.*
import kotlinx.android.synthetic.main.activity_mail_list.*

class MailListActivity : AppCompatActivity() {

    private val TAG:String = "MailListActivity"

    var mails:java.util.ArrayList<AttachedMail> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail_list)
    }

    /**
     * todo: Parcelable объект передается между экранами, даже если не добавлялись. Нужно сделать проверку, чтобы не создавать в памяти лишние объекты
     */
    override fun onResume() {
        mails = intent.getParcelableArrayListExtra("MAILS") // todo: type mismatch
        var mailListAdapter = MailListAdapter(applicationContext, R.layout.view_mail_rec, mails)
        lw_mailListActivity_mailList.adapter = mailListAdapter
        lw_mailListActivity_mailList.setOnItemClickListener { parent, view, position, id ->
            mails.removeAt(position)
            mailListAdapter.notifyDataSetChanged()
        }
        setListeners()
        super.onResume()
    }

    private fun setListeners() {
        imageButton_mailsList_backButton.setOnClickListener { sendMailsToMainActivity() }
        button_addMail.setOnClickListener {
            var input:String = et_mailInput.text.toString()
            et_mailInput.text.clear()
            mails.add(AttachedMail(input))
            var mailListAdapter = MailListAdapter(applicationContext, R.layout.view_mail_rec, mails)
            lw_mailListActivity_mailList.adapter = mailListAdapter
            mailListAdapter.notifyDataSetChanged()
            Log.d(TAG, "button_addMail_onClick: mails - $mails")
        }
    }

    private fun sendMailsToMainActivity() {
        var mainActivityIntent:Intent = Intent()
        mainActivityIntent.putExtra("MAILS", mails)
        setResult(Activity.RESULT_OK, mainActivityIntent)
        finish()
    }
    override fun onBackPressed() = sendMailsToMainActivity()
}
