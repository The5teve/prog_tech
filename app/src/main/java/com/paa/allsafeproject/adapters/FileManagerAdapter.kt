package com.paa.allsafeproject.adapters

/**
 * ЗАМЕНЕН FileRecycledListAdapter
 */



//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ArrayAdapter
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.core.content.ContextCompat
//import com.paa.allsafeproject.R
//import java.io.File
//
//class FileManagerAdapter(context: Context, var resource: Int, var files: Array<File>) :
//    ArrayAdapter<File>(context, resource, files) {
//
//    private val TAG: String = "FILE_MANAGER_ADAPTER"
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val inflater = LayoutInflater.from(context)
//        val listElement = inflater.inflate(R.layout.view_filemanager_element, parent, false)
//        val name = listElement.findViewById<TextView>(R.id.tw_fileManagerElement_name)
//        name.text = files[position].name
//        val icon = listElement.findViewById(R.id.iw_fileManagerElement_icon) as ImageView
//        if (files[position].isDirectory) {
//            icon.setImageResource(R.drawable.ic_folder)
//        } else {
//            name.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDarkOrange))
//            icon.setImageResource(R.drawable.ic_file)
//        }
////        Log.d(TAG, "drawed - ${files[position].path} at $position")
//        return listElement
//    }
//}