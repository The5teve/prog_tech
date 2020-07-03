package com.paa.allsafeproject.web

import okhttp3.*
import okio.ByteString

/**
 * Не используется
 * НЕ ПОДДЕРЖИВАЕТСЯ СЕРВЕРОМ
 */


class WebSocketConnection(private val wsAddress: String, private val activityTransferInterface:DataTransmitInterface)
    : WebSocketListener(), Runnable {

//    var spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//        .tlsVersions(TlsVersion.TLS_1_2)
//        .cipherSuites(
//            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
//            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
//        )
//        .build()

//    private val client: OkHttpClient = OkHttpClient.Builder().connectionSpecs(Collections.singletonList(spec)).build()
    private val client: OkHttpClient = OkHttpClient()
    private val NORMAL_CLOSURE_STATUS = 1000
    private val TAG:String = "WebSocketConnection"

    interface DataTransmitInterface {
        fun transmitByteString(bs: ByteString) // todo: как обрабатывать поступающие в Activity данные? Передаются только строки и потоки байт. Добавить enum класс коды для типа инпута в активити?
        fun transmitString(str: String) // Понадобится ли? Вроде передаваться от сервера будет только поток байт
    }

    override fun run() {
        val request = Request.Builder().url(wsAddress).build()
        val listener = this // ServerWebSocketConnection Наследуется от WebSocketListener,  поэтому указываем this
        val ws = client!!.newWebSocket(request, listener)
//        client!!.dispatcher().executorService().shutdown() // Закрывает соединение?
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
//        Log.d(TAG, "protocols - ${client.protocols()}")
        webSocket.send(ByteString.encodeUtf8("Hello, it's a test message : type String!"))
        webSocket.send("What's up ?")
//            webSocket.send(ByteString.decodeHex("deadbeef"))
        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        activityTransferInterface.transmitString("Received message : $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        activityTransferInterface.transmitByteString(bytes)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        client!!.dispatcher().executorService().shutdown() // Закрывает соединение?
        activityTransferInterface.transmitString("Closing : $code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        activityTransferInterface.transmitString("onFailure : " + t.message)
    }
}