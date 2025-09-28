package com.acsun.pos.di

import com.acsun.pos.data.model.TokenManager
import com.acsun.pos.di.common.Context
import com.russhwolf.settings.Settings
import org.koin.dsl.module

fun appModule(context: Context?) = module {
    includes(networkModule, repositoryModule, viewModelModule)
    single { Settings() }
    single { TokenManager(get()) }
}
