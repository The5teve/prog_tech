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

class FileListAdapter(context: Context, @LayoutRes val resource:Int, val files:ArrayList<AttachedFile>) :
    ArrayAdapter<AttachedFile>(context, resource, files) {

    private val TAG:String = "FILE_LIST_ADAPTER"

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val listElement = inflater.inflate(R.layout.view_file_rec, parent, false)
        listElement.findViewById<TextView>(R.id.tw_fileRecord_name).text = files[position].name
        listElement.findViewById<TextView>(R.id.tw_fileRecord_path).text = files[position].path
        listElement.findViewById<TextView>(R.id.tw_fileRecord_size).text = "${(files[position].size).div(1024)} Kb"
        return listElement
    }
}