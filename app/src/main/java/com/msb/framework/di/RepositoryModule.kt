package com.msb.framework.di

import org.koin.dsl.module
import xyz.teknol.core.auth.data.AuthRepository
import xyz.teknol.core.profile.data.ProfileRepository
import xyz.teknol.core.totd.data.CreateTodRepository
import xyz.teknol.core.user.data.UserRepository

val repositoryModule = module {
    single {
        AuthRepository(get())
    }
    single {
        ProfileRepository(get())
    }
    single {
        CreateTodRepository(get())
    }
    single {
        UserRepository(get())
    }
}

