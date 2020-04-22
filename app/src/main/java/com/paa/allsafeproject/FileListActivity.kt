package com.paa.allsafeproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.paa.allsafeproject.adapters.FileListAdapter
import com.paa.allsafeproject.data_structs.AttachedFile
import kotlinx.android.synthetic.main.activity_file_list.*

class FileListActivity : AppCompatActivity() {

    private val TAG:String = "FileListActivity"

    private val files: ArrayList<AttachedFile> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_list)
    }

    override fun onResume() {
        setListeners()
        // Отображение данных
        // Тестовые данные
        val f1: AttachedFile =
            AttachedFile("/storage/emulated/0/DCIM/edge.ppmx")
        val f2: AttachedFile =
            AttachedFile("/storage/emulated/0/Music/Daughter - Youth.mp3")
        val f3: AttachedFile =
            AttachedFile("/storage/emulated/0/.appodeal")
        val f4: AttachedFile =
            AttachedFile("/storage/emulated/0/DCIM/edge.ppmx")
        val f5: AttachedFile =
            AttachedFile("/storage/emulated/0/Music/Daughter - Youth.mp3")
        val f6: AttachedFile =
            AttachedFile("/storage/emulated/0/.appodeal")
        val f7: AttachedFile =
            AttachedFile("/storage/emulated/0/DCIM/edge.ppmx")
        val f8: AttachedFile =
            AttachedFile("/storage/emulated/0/Music/Daughter - Youth.mp3")
        val f9: AttachedFile =
            AttachedFile("/storage/emulated/0/.appodeal")
        files.add(f1)
        files.add(f2)
        files.add(f3)
        files.add(f4)
        files.add(f5)
        files.add(f6)
        files.add(f7)
        files.add(f8)
        files.add(f9)
        val adapter = FileListAdapter(
            applicationContext,
            R.layout.view_file_rec,
            files
        )
        lw_fileListActivity_fileList.adapter = adapter
        // parent - view родителя нажатого элемента; view - нажатый элемент; position - порядковый номер пункта в списке; id - идентификатор нажатого элемента
        lw_fileListActivity_fileList.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(applicationContext, "File ${files[position].name} removed from list", Toast.LENGTH_LONG).show()
            files.removeAt(position)
            adapter.notifyDataSetChanged()
        }
        Log.d(TAG, "onResume")
        super.onResume()
    }

    private fun setListeners() {
        button_addFile.setOnClickListener { /* todo: add file */ }
        imageButton_fileList_backButton.setOnClickListener { sendFilesToMainActivity(files) }
    }

    override fun onBackPressed() = sendFilesToMainActivity(files)

    private fun sendFilesToMainActivity(files: ArrayList<AttachedFile>?) {
        val toMainActivity = Intent()
        toMainActivity.putExtra("FILES", files)
        setResult(RESULT_OK, toMainActivity)
        finish()
    }
}
