package xyz.teknol.utils.permission

interface PermissionDelegates {

    fun onPermissionGranted(permissions: List<String>)

    fun onPermissionDenied(permissions: List<String>)
}