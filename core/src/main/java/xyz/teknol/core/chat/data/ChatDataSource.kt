package xyz.teknol.core.chat.data

import xyz.teknol.core.chat.domain.SendMessageData
import xyz.teknol.core.chat.domain.SendMessageParams
import xyz.teknol.core.domain.Either
import xyz.teknol.core.domain.Failure

interface ChatDataSource {
    suspend fun sendMessage(params: SendMessageParams): Either<Failure, SendMessageData>
}