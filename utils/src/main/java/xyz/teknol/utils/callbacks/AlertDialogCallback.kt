package xyz.teknol.utils.callbacks

interface AlertDialogCallback {
    fun onPositiveButton(dialogId: Int)
    fun onNegativeButton(dialogId: Int)
}