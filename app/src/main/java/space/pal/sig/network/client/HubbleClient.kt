package space.pal.sig.network.client

import space.pal.sig.BuildConfig

/**
 * SpaceNow
 * Created by Catalin on 12/25/2020
 **/
class HubbleClient : BaseClient() {
    override fun getApiUrl(): String {
        return BuildConfig.HUBBLE_API_URL
    }
}