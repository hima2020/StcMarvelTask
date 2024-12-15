package org.stc.marvel.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import org.stc.marvel.data.repo.MarvelRepository
import org.stc.marvel.domain.api.ApiService
import org.stc.marvel.domain.repo.MarvelsRepositoryImpl

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {
    @Provides
    fun provideMarvelRepository(
        apiService: ApiService
    ): MarvelRepository {
        return MarvelsRepositoryImpl(apiService)
    }
}