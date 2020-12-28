package space.pal.sig.network.client

import space.pal.sig.BuildConfig

/**
SpaceNow
Created by Catalin on 12/28/2020
 **/
class IssClient : BaseClient() {
    override fun getApiUrl(): String {
        return BuildConfig.ISS_API_URL
    }
}