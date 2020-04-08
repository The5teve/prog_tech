package com.paa.allsafeproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_file_list.*

class FileListActivity : AppCompatActivity() {

    private val TAG:String = "FilesListActivity"

    private var files:ArrayList<AttachedFile> = ArrayList<AttachedFile>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_list)
    }

    override fun onResume() {
        setListeners()
        // Отображение данных
        val f1:AttachedFile = AttachedFile("/storage/emulated/0/DCIM/edge.ppmx")
        val f2:AttachedFile = AttachedFile("/storage/emulated/0/Music/Daughter - Youth.mp3")
        val f3:AttachedFile = AttachedFile("/storage/emulated/0/.appodeal")
        files.add(f1)
        files.add(f2)
        files.add(f3)
        val adapter = FileListAdapter(this, R.layout.view_file_rec, files)
//        adapter.getView()
        lw_fileListActivity_fileList.adapter = adapter
        // parent - view родителя нажатого элемента; view - нажатый элемент; position - порядковый номер пункта в списке; id - идентификатор нажатого элемента
        lw_fileListActivity_fileList.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(applicationContext, "File ${files[position].name} removed from list", Toast.LENGTH_LONG).show()
            files.removeAt(position)
            adapter.notifyDataSetChanged()
        }
//        lw_fileListActivity_fileList.setOnItemClickListener { parent, view, position, id ->
//            Toast.makeText(this, (view as TextView).text, Toast.LENGTH_LONG).show()
//        }

        Log.d(TAG, "onResume")
        super.onResume()
    }

    private fun setListeners() {
        button_addFile.setOnClickListener {

        }
        button_removeFile.setOnClickListener {

        }
        imageButton_fileList_backButton.setOnClickListener {
            var mainActivityIntent:Intent = Intent()
//            todo(Передача Intent: путь, название?, размер, лист файлов?)
//            mainActivityIntent.putExtra("FILES_NAMES")
        }
    }
}
