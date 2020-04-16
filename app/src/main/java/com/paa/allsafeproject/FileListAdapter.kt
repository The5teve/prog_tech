package com.paa.allsafeproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FileListAdapter(context: Context, val resource:Int, val files:ArrayList<AttachedFile>) :
    ArrayAdapter<AttachedFile>(context, resource) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = LayoutInflater.from(context)
        val listView = layoutInflater.inflate(R.layout.view_file_rec, parent, false)
        val f_name = listView.findViewById<TextView>(R.id.tw_fileRecord_name)
        val f_path = listView.findViewById<TextView>(R.id.tw_fileRecord_path)
        val f_size = listView.findViewById<TextView>(R.id.tw_fileRecord_size)
        f_name.text = files[position].name
        f_path.text = files[position].path
        f_size.text = "${(files[position].size).div(1024)} Kb"
        return listView
    }
}