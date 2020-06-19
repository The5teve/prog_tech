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
import com.paa.allsafeproject.web.ServerWebSocketConnection
import kotlinx.android.synthetic.main.activity_transmission_status.*
import kotlinx.android.synthetic.main.view_log_message.*
import okhttp3.*
import okio.ByteString
import java.io.File

class TransmissionActivity: AppCompatActivity(), ServerWebSocketConnection.WebSocketConnectionDataTransmitInterface {

    val handler:Handler = Handler()

    private val TAG:String = "TRANSMISSION_ACTIVITY"
    lateinit var files:java.util.ArrayList<AttachedFile> // Сразу преобразовывать в ByteString для передачи?
    lateinit var recipientsFile: File // Сразу преобразовывать в ByteString для передачи?
    private var logList:java.util.ArrayList<LogMessage> = java.util.ArrayList()
    private val logAdapter = LogAdapter(logList)
//    private var client: OkHttpClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transmission_status)

        rv_log_list.adapter = logAdapter
        rv_log_list.layoutManager = LinearLayoutManager(this)
        rv_log_list.setHasFixedSize(true)
        addLog("Created http client")
//        client = OkHttpClient()
        files = intent.getParcelableArrayListExtra("FILES")
        recipientsFile = File(intent.getStringExtra("RECIPIENTS_FILE_PATH"))

        // Запуск потока отсюда - замена startConnection()
        val wsConnectionRunnable:ServerWebSocketConnection = ServerWebSocketConnection("wss://echo.websocket.org",this)
        val wsConnectionThread:Thread = Thread(wsConnectionRunnable)
        wsConnectionThread.start()
//        startConnection()
    }

    private fun addLog(msg: String) {
        Log.d(TAG, "addLog: out - ${msg}")
        logList.add(LogMessage(msg))
        logAdapter.notifyItemInserted(logAdapter.itemCount)
    }

//    private fun startConnection() {
//        val request = Request.Builder().url("wss://echo.websocket.org").build()
//        val listener = TransmissionWebSocketListener()
//        val ws = client!!.newWebSocket(request, listener)
//        client!!.dispatcher().executorService().shutdown() // Закрывает соединение?
//    }

    private fun output(txt: String) {
//        runOnUiThread { // Требуется создание потока для каждого редактирования списка логов

        handler.post {
            tv_status.text = "${tv_status.text}\n $txt"
            addLog(txt)
        }

//            logList.add(LogMessage(txt))
//            logAdapter.notifyItemInserted(logList.size-1) // Возможно не такой индекс
//        }
    }

    override fun transmitByteString(bs: ByteString) {
        output("Received bytes : " + bs.hex())
    }

    override fun transmitString(str: String) {
        output("Received str : $str")
    }

//    private inner class TransmissionWebSocketListener : WebSocketListener() {  // TODO(RENAME)
//        private val NORMAL_CLOSURE_STATUS = 1000
//        private val TAG:String = "TRANSMISSION_LISTENER"
//        override fun onOpen(webSocket: WebSocket, response: Response) {
//            Log.d(TAG, "onOpen")
//            webSocket.send(ByteString.encodeUtf8("Hello, it's a test message !"))
////            webSocket.send("What's up ?")
////            webSocket.send(ByteString.decodeHex("deadbeef"))
//            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !") // закрыть сокет
//        }
//
//        override fun onMessage(webSocket: WebSocket, text: String) {
//            output("Receiving : $text")
//        }
//
//        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//            output("Receiving bytes : " + bytes.hex())
//        }
//
//        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//            webSocket.close(NORMAL_CLOSURE_STATUS, null)
//            output("Closing : $code / $reason")
//        }
//
//        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//            output("Error : " + t.message)
//        }
//    }
}