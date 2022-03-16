package xyz.teknol.core.totd.interactors

import xyz.teknol.core.totd.data.CreateTodRepository
import xyz.teknol.core.totd.domain.CreateTodParams

class CreateTodUseCase(private val repository: CreateTodRepository) {
    suspend operator fun invoke(params: CreateTodParams) = repository.createTod(params)
}

