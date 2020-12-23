package space.pal.sig.util

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import space.pal.sig.database.SpaceNowDatabase
import space.pal.sig.network.NasaApiService
import space.pal.sig.network.SpaceXApiService
import space.pal.sig.network.client.NasaClient
import space.pal.sig.network.client.SpaceXClient
import space.pal.sig.repository.*
import space.pal.sig.view.launches.LaunchesViewModel
import space.pal.sig.view.main.MainViewModel
import space.pal.sig.view.splash.SplashViewModel

/**
SpaceNow
Created by Catalin on 12/6/2020
 **/
object InjectionModule {

    val appModule = module {

        single { SharedPreferencesUtil(androidApplication()) }

        single { SpaceNowDatabase.getInstance(androidApplication()) }
        single { get<SpaceNowDatabase>().apodDao() }
        single { get<SpaceNowDatabase>().launchDao() }
        single { get<SpaceNowDatabase>().roadsterDao() }
        single { get<SpaceNowDatabase>().rocketDao() }
        single { get<SpaceNowDatabase>().launchpadDao() }

        single { NasaClient() }
        single { SpaceXClient() }

        single { get<NasaClient>().createService(NasaApiService::class.java) }
        single { get<SpaceXClient>().createService(SpaceXApiService::class.java) }

        single { ApodRepository(get(), get()) }
        single { RoadsterRepository(get(), get()) }
        single { LaunchRepository(get(), get()) }
        single { RocketRepository(get(), get()) }
        single { LaunchPadRepository(get(), get()) }

        viewModel { SplashViewModel(get()) }
        viewModel { MainViewModel(get(), get(), get(), get()) }
        viewModel { LaunchesViewModel(get(), get()) }

    }

}