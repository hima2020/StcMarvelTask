package org.stc.marvel.domain.utils

import java.security.MessageDigest
import okhttp3.Interceptor
import okhttp3.Response
import org.stc.marvel.provider.DataProvider
import java.util.Locale
import javax.xml.bind.DatatypeConverter

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val timeStamp = System.currentTimeMillis().toString()
        val hash = generateMDFive(timeStamp)
        val original = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apikey", DataProvider.getPublicKey())
            .addQueryParameter("hash", hash)
            .addQueryParameter("ts", timeStamp)
            .build()
        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    private fun generateMDFive(timeStamp: String): String {
        System.loadLibrary("native_lib")
        val message = timeStamp + DataProvider.getPrivateKey() + DataProvider.getPublicKey()
        val md = MessageDigest.getInstance("MD5")
        val hash = md.digest(message.toByteArray())
        return DatatypeConverter.printHexBinary(hash).lowercase(Locale.getDefault())
    }
}


