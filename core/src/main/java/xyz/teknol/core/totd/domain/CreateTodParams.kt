package xyz.teknol.core.totd.domain

data class CreateTodParams(
    val totdContent: String?,
    val userId: String?,
    val totdFilePath: String?
)