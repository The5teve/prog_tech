package com.paa.allsafeproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paa.allsafeproject.R
import com.paa.allsafeproject.data_structs.TreeFile
import kotlinx.android.synthetic.main.view_file_rec_small.view.*
import java.util.ArrayList

class FileRecycledListAdapter(
    val dirFiles: ArrayList<TreeFile>,
    var clickListener: OnFileListItemClickListener) : RecyclerView.Adapter<FileRecycledListAdapter.ViewHolder>() {

    private val TAG: String = "REC_AD_FILE_ADAPTER"

    init {
        Log.d(TAG, "init: current directory files count - ${dirFiles.size}")
    }


    interface OnFileListItemClickListener {
        fun onItemClick(clickedTreeFile: TreeFile, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_file_rec_small, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val currentItem = attachedFiles[position]
//
//        if (currentItem.file.isFile) { holder.ic.setImageResource(R.drawable.ic_file) } else { holder.ic.setImageResource(R.drawable.ic_folder)}
//        holder.name.text = currentItem.name
        holder.initialize(dirFiles[position], clickListener)
    }

    override fun getItemCount(): Int {
        return dirFiles.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val TAG: String = "FILE_RECYCLED_LIST_ADAPTER"

        val ic: ImageView = itemView.ic_file_rec_small_type
        val name: TextView = itemView.tv_file_rec_small_name

        fun initialize(item: TreeFile, action: OnFileListItemClickListener) {
            Log.d("RL_ADAPTER:V_H", "Initialized treeFile - $item")
            if (item.file.isFile) {
                ic.setImageResource(R.drawable.ic_file)
            } else {
                ic.setImageResource(R.drawable.ic_folder)
            } // Установка иконки
            name.text = item.name

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }
    }
}