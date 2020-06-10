package com.paa.allsafeproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.paa.allsafeproject.FileUtils
import com.paa.allsafeproject.R
import com.paa.allsafeproject.adapters.MailListAdapter
import com.paa.allsafeproject.adapters.MainActivityFileListAdapter
import com.paa.allsafeproject.data_structs.AttachedFile
import com.paa.allsafeproject.data_structs.AttachedMail
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



//    private val REQUEST_CODE_OK = 1
//    private val REQUEST_CODE_ERROR = 4

    private val TAG:String = "ACTIVITY_MAIN"

    var mails:ArrayList<AttachedMail> = ArrayList()
    var files:ArrayList<AttachedFile> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        setListeners()
        super.onResume()
    }

    private fun fillMailListView(receivedMails: ArrayList<AttachedMail>?) {
        if (receivedMails != null) {
            mails = receivedMails
            val mailListAdapter:MailListAdapter = MailListAdapter(applicationContext,
                R.layout.view_mail_rec, mails)
            MainActivity_listView_mailList.adapter = mailListAdapter
        } else {
            Log.d(TAG, "fillMailList: ArrayList of received mails is null")
        }
    }

    private fun fillFileListView(receivedFiles: ArrayList<AttachedFile>?) {
        Log.d(TAG, "fillFileList: received ArrayList of AttachedFiles = $receivedFiles")
        if (receivedFiles != null) {
            files = receivedFiles
            var adapter: MainActivityFileListAdapter = MainActivityFileListAdapter(applicationContext,
                R.layout.view_file_rec_small, files)
            MainActivity_ListView_fileList.adapter = adapter
            Log.d(TAG, "files size ${files.size}")
        } else {
//            Toast.makeText(applicationContext, "Cant fill files NullPointer", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setListeners() {
        Log.d(TAG, "setListeners method")
        button_tomails.setOnClickListener {
            val editMailsIntent = Intent(this, MailListActivity::class.java)
            editMailsIntent.putParcelableArrayListExtra("MAILS", mails)
            Log.d(TAG, "Sending ${mails}")
            startActivityForResult(editMailsIntent,1)
        }
        button_toFiles.setOnClickListener {
            val editFilesIntent = Intent(this, FileListActivity::class.java)
            if(files.size!=0) editFilesIntent.putExtra("FILES", files)
            Log.d(TAG, "files.size = ${files.size}")
            startActivityForResult(editFilesIntent,1)
        }
        button_send.setOnClickListener {
            // Создание файла адресатов
            val mailsFile = FileUtils.createMailListFile(mails, applicationContext)
            Log.d(TAG, "Mails file at ${mailsFile.path} : ${mailsFile.length()}")
            // ...
            // Дальнейший процесс работы целесообразно вынести в отдельный класс, контроллирующий сетевое подключение с сервером
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult")
        // отображение почт
        fillMailListView(data?.getParcelableArrayListExtra("MAILS"))
        // отображение файлов
        fillFileListView(data?.getParcelableArrayListExtra("FILES"))
        super.onActivityResult(requestCode, resultCode, data)
    }
}
