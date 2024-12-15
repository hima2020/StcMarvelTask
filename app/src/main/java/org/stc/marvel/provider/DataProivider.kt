package org.stc.marvel.provider

object DataProivider {
    init {
        System.loadLibrary("native_lib")
    }

    // Declare native methods
    external fun getPublicKey(): String
    external fun getPrivateKey(): String
    external fun getBaseUrl(): String
}