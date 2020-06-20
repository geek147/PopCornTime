package com.envios.kitabisa.di

import androidx.room.Room
import com.envios.kitabisa.data.local.db.FavoriteDb
import com.envios.kitabisa.data.remote.MovieFactory
import com.envios.kitabisa.data.repository.MovieRepository
import com.envios.kitabisa.ui.detail.DetailViewModel
import com.envios.kitabisa.ui.favorite.FavoriteViewModel
import com.envios.kitabisa.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.android.ext.koin.androidApplication



val appModules = module {
    single { MovieFactory.create() }
    single { MovieFactory.getHttpLoggingInterceptor() }
    single { MovieFactory.getOkHttpClient(get()) }

    single { MovieRepository(get(), get())}

    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel (get())}
}

val dataModules = module {

    single { Room.databaseBuilder(androidApplication(), FavoriteDb::class.java, "user.db").build() }
    single { get<FavoriteDb>().favoriteDao() }
}
