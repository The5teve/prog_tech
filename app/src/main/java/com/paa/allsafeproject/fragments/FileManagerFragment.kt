package com.paa.allsafeproject.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.paa.allsafeproject.R
import com.paa.allsafeproject.adapters.FileRecycledListAdapter
import com.paa.allsafeproject.data_structs.TreeFile
import kotlinx.android.synthetic.main.fragment_filemanager.*
import java.io.File
import java.lang.ClassCastException
import java.util.ArrayList

class FileManagerFragment(val ROOT_DIR: File) : DialogFragment(),
    FileRecycledListAdapter.OnFileListItemClickListener {

    interface OnFileSelectedInterface {
        fun receivePath(path: String)
    }

    lateinit var onFileSelectedInterface: OnFileSelectedInterface

    private val TAG: String = "FILE_MANAGER_FRAGMENT"

    private var curDir: File = ROOT_DIR
    private var treeFiles: ArrayList<TreeFile> = createTreeFilesArrayList(curDir.listFiles())
    private var adapter: FileRecycledListAdapter = FileRecycledListAdapter(treeFiles, this)


    override fun onAttach(context: Context) {
        try {
            onFileSelectedInterface = activity as OnFileSelectedInterface
        } catch (cce: ClassCastException) {
            Log.e(TAG, "OnAttach: ClassCastException - ${cce.message}")
        }
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_filemanager, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        rv_files.adapter = adapter
        rv_files.layoutManager =
            LinearLayoutManager(activity?.applicationContext) // todo(стоит делать null-проверку?)
        rv_files.setHasFixedSize(true)

        tv_toPreviousDirectory.setOnClickListener {
            if (curDir != ROOT_DIR) {
                updateAdapter(curDir.parentFile)
            }
        }
        super.onActivityCreated(savedInstanceState)
    }

    override fun onItemClick(clickedTreeFile: TreeFile, position: Int) {
        Log.d(TAG, "Clicked ${clickedTreeFile.name} at $position")
        if (clickedTreeFile.file.isDirectory) { // Выбрана директория
            updateAdapter(clickedTreeFile.file)
        } else { // Выбран файл
            onFileSelectedInterface.receivePath(clickedTreeFile.file.absolutePath)
//                    TODO("Передача выбранного файла в родительскую Activity")
        }
    }

    private fun updateAdapter(nextDirectory: File) {
        treeFiles.clear()
        curDir = nextDirectory
        treeFiles.addAll(createTreeFilesArrayList(nextDirectory.listFiles()))
        adapter.notifyDataSetChanged()
    }

//    private fun t_fillFiles(newDirectoryTreeFiles: ArrayList<TreeFile>) {
//        for (position:Int in 0 until newDirectoryTreeFiles.size) {
//            treeFiles.add(newDirectoryTreeFiles[position])
//            adapter.notifyItemInserted(position)
//        }
//    }
//
//    private fun t_clearFiles(adapter: FileRecycledListAdapter) {
//        Log.d(TAG, treeFiles.toString())
//        for (position:Int in 0 until treeFiles.size) {
//            treeFiles.removeAt(position)
//            adapter.notifyItemRemoved(position)
//        }
//    }

    private fun createTreeFilesArrayList(fileList: Array<File>?): ArrayList<TreeFile> {
        Log.d(TAG, "Files received - ${fileList?.size}")
        val treeFileMutableList = ArrayList<TreeFile>()
        if (fileList != null) {
            for (file in fileList) {
                if (file.isHidden) continue // Пропуск скрытых файлов
                var tf = TreeFile(file)
                Log.d(TAG, "createTreeFilesArrayList: created treeFile - $tf")
                treeFileMutableList.add(TreeFile(file))
            }
            Log.d(TAG, "created treeFileList size - ${treeFileMutableList.size}")
            return treeFileMutableList
        }
        return ArrayList()
    }
}