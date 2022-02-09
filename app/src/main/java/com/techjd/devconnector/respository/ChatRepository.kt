package com.techjd.devconnector.respository

import com.techjd.devconnector.api.DevConnectorService
import com.techjd.devconnector.data.models.chat.conversations.Conversations
import com.techjd.devconnector.data.models.chat.messages.Messages
import com.techjd.devconnector.data.models.chat.messages.UserId
import com.techjd.devconnector.data.models.chat.online.MakeOnlineBody
import com.techjd.devconnector.data.models.chat.online.Msg
import com.techjd.devconnector.data.models.chat.onlineusers.OnlineUsers
import retrofit2.Response

object ChatRepository {

    suspend fun makeMeOnline(token: String, makeOnlineBody: MakeOnlineBody): Response<Msg> =
        DevConnectorService.devConnectorInstance.makeMeOnline(token, makeOnlineBody)

    suspend fun removeMeOnline(token: String): Response<Msg> =
        DevConnectorService.devConnectorInstance.removeMeOnline(token)

    suspend fun getAllConversations(token: String): Response<Conversations> =
        DevConnectorService.devConnectorInstance.getAllConversation(token)

    suspend fun getOnlineUsers(token: String): Response<OnlineUsers> =
        DevConnectorService.devConnectorInstance.getAllOnlineUser(token)

    suspend fun getAllMessages(token: String, userId: UserId): Response<Messages> =
        DevConnectorService.devConnectorInstance.getAllMessages(token, userId)
}