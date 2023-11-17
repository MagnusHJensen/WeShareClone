package dk.sdu.weshareclone.model.service.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dk.sdu.weshareclone.model.service.AccountService
import dk.sdu.weshareclone.model.service.ProfileService
import dk.sdu.weshareclone.model.service.impl.AccountServiceImpl
import dk.sdu.weshareclone.model.service.impl.ProfileServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds abstract fun provideProfileService(impl: ProfileServiceImpl): ProfileService
}