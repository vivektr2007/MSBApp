package com.msb.framework.di

import org.koin.dsl.module
import xyz.teknol.core.auth.data.AuthDataSource
import xyz.teknol.core.profile.data.ProfileDataSource
import xyz.teknol.core.totd.data.CreateTodDataSource
import xyz.teknol.core.user.data.UserDataSource
import xyz.teknol.network.dataSources.ServerAuthDataSource
import xyz.teknol.network.dataSources.ServerCreateTodDataSource
import xyz.teknol.network.dataSources.ServerProfileDataSource
import xyz.teknol.network.dataSources.ServerUserDataSource

val dataSourceModule = module {
    single<AuthDataSource> {
        ServerAuthDataSource(get(), get())
    }
    single<ProfileDataSource> {
        ServerProfileDataSource(get())
    }
    single<CreateTodDataSource> {
        ServerCreateTodDataSource(get())
    }
    single<UserDataSource> {
        ServerUserDataSource(get())
    }
}