package com.techjd.devconnector.api

import android.util.Log
import com.techjd.devconnector.Utils.Constants
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object DevConnectorChat {
    private var mSocket: Socket = try {
        IO.socket(Constants.CHAT_SERVER_URL)
    } catch (e: URISyntaxException) {
        throw RuntimeException(e)
    }

    val socket: Socket
        get() = mSocket
}