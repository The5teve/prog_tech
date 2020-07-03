package com.paa.allsafeproject.web;

import android.util.Log;

import com.paa.allsafeproject.data_structs.AttachedFile;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class SocketConnection {

    private  Socket  mSocket = null;
    private  String  mHost   = null;
    private  int     mPort   = 0;
    private WebSocketConnection.DataTransmitInterface activityTransmitInterface;

    private final String LOG_TAG = "SOCKET_CONNECTION";

    public SocketConnection(final String host, final int port, WebSocketConnection.DataTransmitInterface transmitInterface)
    {
        this.mHost = host;
        this.mPort = port;
        this.activityTransmitInterface = transmitInterface;
    }

    // Метод открытия сокета
    public void openConnection() throws Exception {
        Log.d(LOG_TAG, "openConnection: closing previous connection");
        // Если сокет уже открыт, то он закрывается
        closeConnection();
        try {
            // Создание сокета
            mSocket = new Socket(mHost, mPort);
            Log.d(LOG_TAG, "openConnection: creating connection "+mSocket);
        } catch (IOException e) {
            activityTransmitInterface.transmitString("Невозможно создать сокет:"+ e.getMessage());
//            throw new Exception("Невозможно создать сокет: "
//                    + e.getMessage());
        }

        // Слушатель входящих данных
        new Thread(new Runnable(){
            @Override
            public void run() {
                if (mSocket == null) activityTransmitInterface.transmitString("Socket is null");
                else {
                    try {
                        InputStream input = mSocket.getInputStream();
                        InputStreamReader reader = new InputStreamReader(input);
                        int character;
                        StringBuilder data = new StringBuilder();
                        while ((character = reader.read()) != -1) {
                            data.append((char) character);
                            Log.d(LOG_TAG, data.toString());
                        }
                        activityTransmitInterface.transmitString(data.toString());
                        Log.d(LOG_TAG, "data - " + data.toString());
                    } catch (IOException e) {
                        Log.d(LOG_TAG, "LISTENING THREAD : " + e.getMessage());
                    }
                }
            }
        }).start();

        Thread.sleep(2000);

    }

    /**
     * Метод закрытия сокета
     */
    public void closeConnection() {
        if (mSocket != null && !mSocket.isClosed()) {
            try {
                Log.d(LOG_TAG, "closing connection");
                mSocket.close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Ошибка при закрытии сокета :"
                        + e.getMessage());
            } finally {
                mSocket = null;
            }
        }
        mSocket = null;
    }

    /**
     * Метод отправки данных
     */
    public void sendData(byte[] data) throws Exception {
        // Проверка открытия сокета
        if (mSocket == null || mSocket.isClosed()) {
            throw new Exception("Ошибка отправки данных. " +
                    "Сокет не создан или закрыт");
        }
        // Отправка данных
        try {
            mSocket.getOutputStream().write(data);
            mSocket.getOutputStream().flush();
        } catch (IOException e) {
            throw new Exception("Ошибка отправки данных : "
                    + e.getMessage());
        }
    }
    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
        closeConnection();
    }
}
