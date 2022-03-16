package xyz.teknol.core.totd.domain

data class CreateTodData(
    val totdContent: String?,
    val totdFilePath: String?,
    val userId: String?
)