package xyz.teknol.database

import android.content.Context

class SharedPreferenceManager(private val context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("PreTo3", android.content.Context.MODE_PRIVATE)

    fun setUsername(username: String) {
        sharedPreferences.edit().putString("UserName", username).apply()
    }

    fun getUsername() = sharedPreferences.getString("UserName", "")

    fun setEmail(username: String) {
        sharedPreferences.edit().putString("Email", username).apply()
    }

    fun getEmail() = sharedPreferences.getString("Email", "")

    fun setMesiboUserDetails(userDetails: String) {
        sharedPreferences.edit().putString("MesiboUser", userDetails).apply()
    }

    fun getMesiboUserDetails() = sharedPreferences.getString("MesiboUser", "")

    fun setMesiboToken(MesiboToken: String) {
        sharedPreferences.edit().putString("MesiboToken", MesiboToken).apply()
    }

    fun getMesiboUserToken() = sharedPreferences.getString("MesiboToken", "")

    fun setMesiboUid(uid: String) {
        sharedPreferences.edit().putString("uid", uid).apply()
    }

    fun getMesiboUserUid() = sharedPreferences.getString("uid", "")

    fun setId(id: String) {
        sharedPreferences.edit().putString("id", id).apply()
    }

    fun getId() = sharedPreferences.getString("id", "")


    fun setMobile(id: String) {
        sharedPreferences.edit().putString("mobile", id).apply()
    }

    fun getMobile() = sharedPreferences.getString("mobile", "")


    fun setMsbPin(id: String) {
        sharedPreferences.edit().putString("msbPin", id).apply()
    }

    fun getMsbPin() = sharedPreferences.getString("msbPin", "")

    fun setPassword(password: String) {
        sharedPreferences.edit().putString("Password", password).apply()
    }

    fun getPassword() = sharedPreferences.getString("Password", "")

    fun setLoggedIn(login: Boolean) {
        sharedPreferences.edit().putBoolean("LoggedIn", login).apply()
    }

    fun isLoggedIn() = sharedPreferences.getBoolean("LoggedIn", false)

}