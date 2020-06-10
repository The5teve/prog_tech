package com.paa.allsafeproject.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.paa.allsafeproject.fragments.FileManagerFragment
import com.paa.allsafeproject.R
import com.paa.allsafeproject.adapters.FileListAdapter
import com.paa.allsafeproject.data_structs.AttachedFile
import kotlinx.android.synthetic.main.activity_file_list.*
import java.io.File

class FileListActivity : AppCompatActivity(), FileManagerFragment.OnFileSelectedInterface {

//    companion object FileReceiver {
//        fun sendPath(p:String) {
//
//        }
//    }

    private val TAG:String = "FILE_LIST_ACTIVITY"

    var files: java.util.ArrayList<AttachedFile> = ArrayList()

    var fragment: FileManagerFragment = FileManagerFragment(Environment.getExternalStorageDirectory())
    val manager = supportFragmentManager

    private lateinit var adapter:FileListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
//            Log.d(TAG, "Read permission granted")
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_list)
    }

    override fun onResume() {
        if(intent.getParcelableArrayExtra("FILES") != null) {
            files = intent.getParcelableArrayListExtra("FILES")
        }
        Log.d(TAG, "files.size = ${files.size}")
        setListeners()
        attachAdapter()
//        Log.d(TAG, "onResume")
        // Отображение данных
        // Тестовые данные
//        val f1: AttachedFile =
//            AttachedFile("/storage/emulated/0/DCIM/edge.ppmx")
//        val f2: AttachedFile =
//            AttachedFile("/storage/emulated/0/Music/Daughter - Youth.mp3")
//        val f3: AttachedFile =
//            AttachedFile("/storage/emulated/0/.appodeal")
//        val f4: AttachedFile =
//            AttachedFile("/storage/emulated/0/DCIM/edge.ppmx")
//        val f5: AttachedFile =
//            AttachedFile("/storage/emulated/0/Music/Daughter - Youth.mp3")
//        val f6: AttachedFile =
//            AttachedFile("/storage/emulated/0/.appodeal")
//        val f7: AttachedFile =
//            AttachedFile("/storage/emulated/0/DCIM/edge.ppmx")
//        val f8: AttachedFile =
//            AttachedFile("/storage/emulated/0/Music/Daughter - Youth.mp3")
//        val f9: AttachedFile =
//            AttachedFile("/storage/emulated/0/.appodeal")
//        files.add(f1)
//        files.add(f2)
//        files.add(f3)
//        files.add(f4)
//        files.add(f5)
//        files.add(f6)
//        files.add(f7)
//        files.add(f8)
//        files.add(f9)
//        adapter = FileListAdapter(
//            applicationContext,
//            R.layout.view_file_rec,
//            files
//        )
//        lw_fileListActivity_fileList.adapter = adapter
        // parent - view родителя нажатого элемента; view - нажатый элемент; position - порядковый номер пункта в списке; id - идентификатор нажатого элемента
        lw_fileListActivity_fileList.setOnItemClickListener { parent, view, position, id ->
//            Toast.makeText(applicationContext, "File ${files[position].name} removed from list", Toast.LENGTH_LONG).show()
//            Toast.makeText(applicationContext, "context.getExternalFilesDir(null)?.absolutePath - ${applicationContext.getExternalFilesDir(null)?.list()}", Toast.LENGTH_LONG).show()
            files.removeAt(position)
            adapter.notifyDataSetChanged()
        }
        super.onResume()
    }

    private fun attachAdapter() {
        adapter = FileListAdapter(
            applicationContext,
            R.layout.view_file_rec,
            files
        )
        lw_fileListActivity_fileList.adapter = adapter
    }

    private fun setListeners() {
        button_addFile.setOnClickListener {
            showFileManagerFragment()
//            val dialog = FilePickerDialog(this@FileListActivity)
//            fileDialog.setMessage("sdadsad")

//            val data_test:Array<String> = arrayOf("ads","asasda", "sdadasd")
//            val testroot = File(applicationContext.filesDir.absolutePath)
//            val adapter: ArrayAdapter<String> = ArrayAdapter(applicationContext, android.R.layout.select_dialog_item, testroot.list())
//            dialog.setAdapter(adapter, null)

//            dialog.show()
        }
        imageButton_fileList_backButton.setOnClickListener { sendFilesToMainActivity(files) }
    }

    private fun showFileManagerFragment() {
//        val transaction:FragmentTransaction = manager.beginTransaction()
//        transaction.replace(R.id.fragment, fragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
        fragment.dialog?.setTitle("FileManagerFragment")
        fragment.show(manager, "TAG")
    }

    override fun onBackPressed() = sendFilesToMainActivity(files)

    private fun sendFilesToMainActivity(files: ArrayList<AttachedFile>?) {
        val toMainActivity = Intent()
        toMainActivity.putExtra("FILES", files)
        setResult(RESULT_OK, toMainActivity)
        finish()
    }

    // Переопределенный метод в родительской Activity
    override fun receivePath(path: String) {
        Toast.makeText(applicationContext, "$TAG : receivePath: Received path - $path", Toast.LENGTH_LONG).show()
        files.add(AttachedFile(path))
        adapter.notifyDataSetChanged()
        manager.popBackStack()
    }
}
