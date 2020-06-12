package com.paa.allsafeproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.paa.allsafeproject.R
import com.paa.allsafeproject.data_structs.AttachedFile

class MainActivityFileListAdapter(context: Context, @LayoutRes val resource: Int, private val files:ArrayList<AttachedFile>) :
    ArrayAdapter<AttachedFile>(context, resource, files) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var inflater = LayoutInflater.from(context)
        var listElement = inflater.inflate(R.layout.view_file_rec_small, parent, false)
        listElement.findViewById<TextView>(R.id.tv_file_rec_small_name).text = files[position].name
        return listElement
    }
}