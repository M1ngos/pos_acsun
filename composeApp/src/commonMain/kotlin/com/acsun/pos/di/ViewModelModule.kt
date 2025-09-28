package com.acsun.pos.di

import com.acsun.pos.presentation.SharedViewModel
import com.acsun.pos.presentation.ui.auth.AuthViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { SharedViewModel(get()) }
    factory { AuthViewModel(get(), get()) }
}
