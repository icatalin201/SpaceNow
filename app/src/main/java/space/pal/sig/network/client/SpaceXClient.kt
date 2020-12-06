package space.pal.sig.network.client

import space.pal.sig.BuildConfig

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
class SpaceXClient : BaseClient() {

    override fun getApiUrl(): String {
        return BuildConfig.SPACE_X_API_URL
    }
}