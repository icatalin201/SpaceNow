package space.pal.sig.util

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import space.pal.sig.database.SpaceNowDatabase
import space.pal.sig.network.HubbleService
import space.pal.sig.network.IssService
import space.pal.sig.network.NasaApiService
import space.pal.sig.network.SpaceXApiService
import space.pal.sig.network.client.HubbleClient
import space.pal.sig.network.client.IssClient
import space.pal.sig.network.client.NasaClient
import space.pal.sig.network.client.SpaceXClient
import space.pal.sig.repository.*
import space.pal.sig.view.apod.ApodViewModel
import space.pal.sig.view.facts.FactsViewModel
import space.pal.sig.view.iss.IssViewModel
import space.pal.sig.view.launch.LaunchViewModel
import space.pal.sig.view.launches.LaunchesViewModel
import space.pal.sig.view.main.MainViewModel
import space.pal.sig.view.news.NewsViewModel
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
        single { get<SpaceNowDatabase>().factDao() }
        single { get<SpaceNowDatabase>().newsDao() }
        single { get<SpaceNowDatabase>().crewMemberDao() }

        single { NasaClient() }
        single { SpaceXClient() }
        single { HubbleClient() }
        single { IssClient() }

        single { get<NasaClient>().createService(NasaApiService::class.java) }
        single { get<SpaceXClient>().createService(SpaceXApiService::class.java) }
        single { get<HubbleClient>().createService(HubbleService::class.java) }
        single { get<IssClient>().createService(IssService::class.java) }

        single { ApodRepository(get(), get()) }
        single { RoadsterRepository(get(), get()) }
        single { LaunchRepository(get(), get(), get()) }
        single { RocketRepository(get(), get()) }
        single { LaunchPadRepository(get(), get()) }
        single { FactRepository(get()) }
        single { NewsRepository(get(), get()) }
        single { CrewMemberRepository(get(), get()) }
        single { IssRepository(get()) }

        viewModel { SplashViewModel(get(), get()) }
        viewModel { MainViewModel(get(), get(), get(), get(), get()) }
        viewModel { LaunchesViewModel(get(), get()) }
        viewModel { FactsViewModel(get(), get()) }
        viewModel { NewsViewModel(get(), get()) }
        viewModel { LaunchViewModel(get(), get(), get(), get()) }
        viewModel { ApodViewModel(get(), get()) }
        viewModel { IssViewModel(get(), get()) }

    }

}