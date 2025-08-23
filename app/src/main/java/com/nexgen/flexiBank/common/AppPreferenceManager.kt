package com.nexgen.flexiBank.common

object AppPreferenceManager {
    private const val IS_LOGIN = "is_login"
    fun setAuth(isLogin: Boolean) {
        ModelPreferencesManager.put(isLogin, IS_LOGIN)
    }

    fun isAuth(): Boolean {
        return ModelPreferencesManager.get<Boolean>(IS_LOGIN) == true
    }
}