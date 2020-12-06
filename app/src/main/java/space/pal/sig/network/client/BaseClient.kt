package space.pal.sig.network.client

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import space.pal.sig.old.repository.client.BaseClient

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
abstract class BaseClient {

    companion object {
        private var CLIENT: Retrofit? = null
    }

    fun <T> createService(service: Class<T>): T {
        return getClient().create(service)
    }

    abstract fun getApiUrl(): String

    private fun getClient(): Retrofit {
        if (CLIENT == null) {
            synchronized(BaseClient::class.java) {
                CLIENT = build(getApiUrl())
            }
        }
        return CLIENT!!
    }

    private fun build(url: String): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        return Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}