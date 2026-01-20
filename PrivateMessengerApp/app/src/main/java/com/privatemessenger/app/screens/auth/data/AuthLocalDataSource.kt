package com.privatemessenger.app.screens.auth.data

import com.tencent.mmkv.MMKV
import javax.inject.Inject

interface AuthLocalDataSource {
    fun getAccessToken(): String?
    fun setAccessToken(token: String?)

    fun getRefreshToken(): String?
    fun setRefreshToken(token: String?)

    fun getPublicKey(): String
    fun setPublicKey(key: String)

    fun getPrivateKey(): String
    fun setPrivateKey(key: String)
}

class AuthLocalDataSourceImpl @Inject constructor() : AuthLocalDataSource {
    private val storage by lazy {
        MMKV.mmkvWithID(
            "AuthLocalDataSource",
            MMKV.MULTI_PROCESS_MODE
        )
    }

    override fun getAccessToken(): String? = storage.decodeString("accessToken", null)

    override fun setAccessToken(token: String?) {
        storage.encode("accessToken", token)
    }

    override fun getRefreshToken(): String? = storage.decodeString("refreshToken", null)

    override fun setRefreshToken(token: String?) {
        storage.encode("refreshToken", token)
    }

    override fun getPublicKey(): String {
        return storage.decodeString("publicKey", "")!!
    }

    override fun setPublicKey(key: String) {
        storage.encode("publicKey", key)
    }

    override fun getPrivateKey(): String {
        return storage.decodeString("privateKey", "")!!
    }

    override fun setPrivateKey(key: String) {
        storage.encode("privateKey", key)
    }
}