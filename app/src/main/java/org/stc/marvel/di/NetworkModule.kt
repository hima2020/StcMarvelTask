package org.stc.marvel.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.stc.marvel.domain.api.ApiService
import org.stc.marvel.domain.utils.AuthInterceptor
import org.stc.marvel.provider.DataProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideMarvelService(okHttpClient: OkHttpClient): ApiService {
        System.loadLibrary("native_lib")
        val retrofit = Retrofit.Builder()
            .baseUrl(DataProvider.getBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())

            .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor, @ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

}