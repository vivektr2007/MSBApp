package xyz.teknol.utils.callbacks

interface ListDialogCallback {
    fun onItemSelected(position: Int, item: String, dialogId: Int)
    fun onDismiss(dialogId: Int)
}