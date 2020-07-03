package com.paa.allsafeproject.activities

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.paa.allsafeproject.R
import com.paa.allsafeproject.adapters.LogAdapter
import com.paa.allsafeproject.data_structs.AttachedFile
import com.paa.allsafeproject.data_structs.LogMessage
import com.paa.allsafeproject.web.SocketConnection
import com.paa.allsafeproject.web.WebSocketConnection
import kotlinx.android.synthetic.main.activity_transmission_status.*
import okio.ByteString
import java.io.File
import java.util.*

class TransmissionActivity: AppCompatActivity(), WebSocketConnection.DataTransmitInterface {

//    private val ECHO_WS_ADDRESS = "wss://echo.websocket.org"
//    private val serverWsAddress = "wss://192.168.1.162:8008"

    private val socketHost = "45.137.190.159"
    private val socketPort = 80


    val handler:Handler = Handler()

    private val TAG:String = "TRANSMISSION_ACTIVITY"
    lateinit var files:java.util.ArrayList<AttachedFile> // Сразу преобразовывать в ByteString для передачи?
    lateinit var recipientsFile: File // Сразу преобразовывать в ByteString для передачи?
    private var logList:java.util.ArrayList<LogMessage> = java.util.ArrayList()
    private val logAdapter = LogAdapter(logList)

    lateinit var socketConnection:SocketConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transmission_status)

        rv_log_list.adapter = logAdapter
        rv_log_list.layoutManager = LinearLayoutManager(this)
        rv_log_list.setHasFixedSize(true)
        files = intent.getParcelableArrayListExtra("FILES")
        recipientsFile = File(intent.getStringExtra("RECIPIENTS_FILE_PATH"))

//        val wsConnectionRunnable:WebSocketConnection = WebSocketConnection(serverWsAddress,this)
//        val wsConnectionThread:Thread = Thread(wsConnectionRunnable)
//        wsConnectionThread.start()

        socketConnection = SocketConnection(socketHost, socketPort, this)

        Thread(Runnable {
            socketConnection.openConnection()
            Thread.sleep(2000)
            sendFilesCount(files.size+1)
            sendFiles(files)
            sendMailList(recipientsFile)
            socketConnection.closeConnection()
        }).start()
    }

    fun sendMailList(mailFile: File) {
        socketConnection.sendData(mailFile.readBytes())
    }

    fun sendFiles(files: ArrayList<AttachedFile>) {
        for (attachedFile in files) {
            socketConnection.sendData(attachedFile.fileObj.name.toByteArray())
            socketConnection.sendData(formFile(attachedFile.fileObj))
        }
    }

    private fun formFile(fileObj: File): ByteArray? {
        return fileObj.readBytes()+"b'stop'".toByteArray()
    }

    fun sendFilesCount(filesCount: Int) {
        socketConnection.sendData(filesCount.toString().toByteArray())
    }


    private fun addLog(msg: String) {
        Log.d(TAG, "addLog: out - ${msg}")
        logList.add(LogMessage(msg))
        logAdapter.notifyItemInserted(logAdapter.itemCount)
    }

    private fun output(txt: String) {
        handler.post {
            tv_status.text = "${tv_status.text}\n $txt"
//            addLog(txt) todo: Фикс добавления в RView логов
        }
//            logList.add(LogMessage(txt))
//            logAdapter.notifyItemInserted(logList.size-1) // Возможно не такой индекс
//        }
    }

    override fun transmitByteString(bs: ByteString) {
        output(bs.utf8())
    }

    override fun transmitString(str: String) {
        output(str)
    }
}