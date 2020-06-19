package com.paa.allsafeproject.web

import android.util.Log
import okhttp3.*
import okio.ByteString

class ServerWebSocketConnection(private val wsAddress: String, private val activityTransferInterface:WebSocketConnectionDataTransmitInterface)
    : WebSocketListener(), Runnable {

    private val client: OkHttpClient = OkHttpClient()
    private val NORMAL_CLOSURE_STATUS = 1000
    private val TAG:String = "TRANSMISSION_LISTENER"

    interface WebSocketConnectionDataTransmitInterface {
        fun transmitByteString(bs: ByteString) // todo: как обрабатывать поступающие в Activity данные? Передаются только строки и потоки байт. Добавить enum класс коды для типа инпута в активити?
        fun transmitString(str: String) // Понадобится ли? Вроде передаваться от сервера будет только поток байт
    }

    override fun run() {
        val request = Request.Builder().url(wsAddress).build()
        val listener = this // ServerWebSocketConnection Наследуется от WebSocketListener,  поэтому указываем this
        val ws = client!!.newWebSocket(request, listener)
        client!!.dispatcher().executorService().shutdown() // Закрывает соединение?
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d(TAG, "onOpen")
        webSocket.send(ByteString.encodeUtf8("Hexed text here"))
        webSocket.send("Hello, it's a test message : type String!")
//            webSocket.send("What's up ?")
//            webSocket.send(ByteString.decodeHex("deadbeef"))
        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !") // закрыть сокет
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        activityTransferInterface.transmitString("Received message : $text")
    }

    /**
     * todo: кроме String может быть необходимо передавать ByteString(возможно ключ шифрвоания будет представлен не в виде строки)
     * todo: В любом случае нужно реализовать передачу и ByteString
     */
//    private fun output(str: String) {
//        activityTransferInterface.transmitString(str)
//    }
//
//    private fun outputByteString(byteString:ByteString) {
//        activityTransferInterface.transmitByteString(byteString)
//    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        activityTransferInterface.transmitByteString(bytes)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        activityTransferInterface.transmitString("Closing : $code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        activityTransferInterface.transmitString("Error : " + t.message)
    }
}