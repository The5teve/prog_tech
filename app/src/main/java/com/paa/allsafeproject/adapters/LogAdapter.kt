package com.paa.allsafeproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paa.allsafeproject.R
import com.paa.allsafeproject.data_structs.LogMessage
import kotlinx.android.synthetic.main.view_log_message.view.*
import java.util.ArrayList

class LogAdapter(private var logMessages:ArrayList<LogMessage>) : RecyclerView.Adapter<LogAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_logMessage: TextView = itemView.tv_logmsg_text

        fun initialize(item: LogMessage) {
            tv_logMessage.text = item.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_log_message, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return logMessages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val curItem = logMessages[position]
//        holder.tv_logMessage.text = curItem.message
        holder.initialize(logMessages[position])
    }
}