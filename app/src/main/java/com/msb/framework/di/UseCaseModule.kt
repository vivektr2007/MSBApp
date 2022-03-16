package com.msb.framework.di

import org.koin.dsl.module
import xyz.teknol.core.auth.interactors.*
import xyz.teknol.core.profile.interactors.GetAnonymousProfileUseCase
import xyz.teknol.core.profile.interactors.UpdateAnonymousProfileUseCase
import xyz.teknol.core.profile.interactors.UploadFileUseCase
import xyz.teknol.core.totd.interactors.CreateTodUseCase
import xyz.teknol.core.user.interactors.UserListUseCase

val useCaseModule = module {
    single {
        SignUpUseCase(get())
    }
    single {
        SignInUseCase(get())
    }
    single {
        GetUserProfileUseCase(get())
    }
    single {
        UpdateUserProfileUseCase(get())
    }
    single {
        GetAnonymousProfileUseCase(get())
    }
    single {
        UpdateAnonymousProfileUseCase(get())
    }
    single {
        ChangePasswordUseCase(get())
    }
    single {
        UploadFileUseCase(get())
    }

    single {
        CreateTodUseCase(get())
    }

    single {
        UserListUseCase(get())
    }

}