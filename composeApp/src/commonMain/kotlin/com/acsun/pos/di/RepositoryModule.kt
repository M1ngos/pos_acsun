package com.acsun.pos.di

import com.acsun.pos.data.repository.AuthRepository
import com.acsun.pos.data.repository.AuthRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}
