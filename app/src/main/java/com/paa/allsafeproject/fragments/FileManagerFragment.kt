package com.paa.allsafeproject.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paa.allsafeproject.R
import com.paa.allsafeproject.adapters.FileManagerAdapter
import kotlinx.android.synthetic.main.fragment_filemanager.*
import java.io.File
import java.lang.ClassCastException

class FileManagerFragment(val ROOT_DIR:File): DialogFragment() {

    interface OnFileSelectedInterface {
        fun receivePath(path:String)
    }

    lateinit var onFileSelectedInterface: OnFileSelectedInterface

    private val TAG:String = "FILE_MANAGER_FRAGMENT"

    private var curDir:File = ROOT_DIR
    private var files: Array<File> = curDir.listFiles()
    private lateinit var adapter: FileManagerAdapter

    override fun onAttach(context: Context) {
        try {
            onFileSelectedInterface = activity as OnFileSelectedInterface
        } catch (cce:ClassCastException) {
            Log.e(TAG, "OnAttach: ClassCastException - ${cce.message}")
        }
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_filemanager, container,false)
        adapter = FileManagerAdapter(context!!,
            R.id.lw_files, files)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        lw_files.adapter = adapter

        tw_toPreviousDirectory.setOnClickListener {
            if (curDir != ROOT_DIR) {
                curDir = curDir.parentFile
                files = curDir.listFiles()
                adapter.files = files
                adapter.notifyDataSetChanged()
            }
        }

        lw_files.setOnItemClickListener { parent, view, position, id ->
            val clickedFile: File? = files.get(position)
            Log.d(TAG, "Clicked ${clickedFile?.name} at $position")
            if (clickedFile != null) {
                if (clickedFile.isDirectory) { // Выбрана директория
                    curDir = clickedFile
                    files = curDir.listFiles()
                    adapter.files = files
                    printFiles(adapter.files)
                    adapter.notifyDataSetChanged()
                } else {
                    onFileSelectedInterface.receivePath(clickedFile.absolutePath)

                    // Выбран файл
//                    TODO("Передача выбранного файла в родительскую Activity")
                }
            }
        }
        super.onActivityCreated(savedInstanceState)
    }

    private fun printFiles(d:Array<File>):Unit {
        if (d.size == 0) Log.d(TAG, "Dir is empty")
        Log.d(TAG, "Listing files of size - ${d.size}")
        for (f in d) {
            Log.d(TAG, "File - ${f.path}")
        }
    }
}