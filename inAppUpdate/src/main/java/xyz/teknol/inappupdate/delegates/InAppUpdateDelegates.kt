package xyz.teknol.inappupdate.delegates

interface InAppUpdateDelegates {
    fun updateProgress(bytesDownloaded: Long, totalBytesToDownload: Long)
    fun updateDownloaded()
    fun updateFailed()
}