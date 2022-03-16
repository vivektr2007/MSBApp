package xyz.teknol.core.chat.interactors

import xyz.teknol.core.chat.data.ChatRepository
import xyz.teknol.core.chat.domain.SendMessageParams
import xyz.teknol.core.profile.data.ProfileRepository

class SendMessageUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(params: SendMessageParams) = repository.sendMessage(params)
}

