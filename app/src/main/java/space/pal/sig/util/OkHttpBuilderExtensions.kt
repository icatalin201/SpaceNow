package space.pal.sig.util

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
@SuppressLint("TrustAllX509TrustManager")
object OkHttpBuilderExtensions {

    fun OkHttpClient.Builder.setupTrustManager(): OkHttpClient.Builder {
        try {
            val trustAllCerts = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
            )
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            this.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            this.hostnameVerifier { _: String?, _: SSLSession? -> true }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        return this
    }

}