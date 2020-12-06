package space.pal.sig.util

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import space.pal.sig.network.client.NasaClient
import space.pal.sig.network.client.SpaceXClient

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
object InjectionModule {

    val appModule = module {

        single { SharedPreferencesUtil(androidApplication()) }

        single { NasaClient() }
        single { SpaceXClient() }

    }

}