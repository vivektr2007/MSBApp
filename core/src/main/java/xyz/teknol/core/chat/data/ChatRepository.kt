package xyz.teknol.core.chat.data

import xyz.teknol.core.chat.domain.SendMessageData
import xyz.teknol.core.chat.domain.SendMessageParams
import xyz.teknol.core.domain.Either
import xyz.teknol.core.domain.Failure
import xyz.teknol.core.profile.domain.AnonymousProfileData
import xyz.teknol.core.profile.domain.UpdateAnonymousProfileParams
import xyz.teknol.core.profile.domain.UploadUserProfileData

class ChatRepository(private val dataSource: ChatDataSource) {
    suspend fun sendMessage(params: SendMessageParams): Either<Failure, SendMessageData> =
        dataSource.sendMessage(params)


}