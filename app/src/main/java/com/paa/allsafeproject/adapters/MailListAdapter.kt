package com.paa.allsafeproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.paa.allsafeproject.R
import com.paa.allsafeproject.data_structs.AttachedMail

class MailListAdapter(context: Context, resource: Int,val mails: ArrayList<AttachedMail>) :
    ArrayAdapter<AttachedMail>(context, resource, mails) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val mailListElement = inflater.inflate(R.layout.view_mail_rec, parent, false)
        mailListElement.findViewById<TextView>(R.id.tw_mail_rec_small_name).text = mails[position].mail
        return mailListElement
    }
}