package com.example.appiness.core.application.di.bakers

import com.danbro.delivery.core.application.di.login.BakerScope
import com.example.appiness.core.application.di.ViewModelProviderFactory
import com.example.appiness.data.datasource.RemoteApi
import com.example.appiness.data.datasource.RemoteApiImpl
import com.example.appiness.data.repository.BakersRepository
import com.example.appiness.domain.remoteusecase.BakersRemoteDataUseCase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class BakersModule {
    @Provides
    @BakerScope
    fun provideSignUpRemoteApi(retrofit: Retrofit): RemoteApi {
        return retrofit.create<RemoteApi>(RemoteApi::class.java)
    }

    @Provides
    @BakerScope
    fun provideRemoteSignUpDataSourceImp(remoteApi: RemoteApi): BakersRepository {
        return RemoteApiImpl(remoteApi)
    }

    @Provides
    @BakerScope
    fun provideSignUpRemoteDataUseCase(remoteSignUpData: BakersRepository): BakersRemoteDataUseCase {
        return BakersRemoteDataUseCase(remoteSignUpData)
    }

    @Provides
    @BakerScope
    @Named("BakersActivity")
    fun provideLoginViewModelFactory(
        bakersUseCase: BakersRemoteDataUseCase
    ): ViewModelProviderFactory {
        return ViewModelProviderFactory(bakersUseCase)
    }
}