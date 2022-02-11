package com.techjd.devconnector.api

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object DevConnectorChat {
    private var mSocket: Socket = try {
        IO.socket("http://192.168.2.4:5000/")
    } catch (e: URISyntaxException) {
        throw RuntimeException(e)
    }

    val socket: Socket
        get() = mSocket
}