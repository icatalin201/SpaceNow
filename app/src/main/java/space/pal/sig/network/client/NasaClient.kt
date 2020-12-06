package space.pal.sig.network.client

import space.pal.sig.BuildConfig

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
class NasaClient : BaseClient() {

    override fun getApiUrl(): String {
        return BuildConfig.NASA_API_URL
    }
}