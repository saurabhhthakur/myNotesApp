package com.myproject.notes.hilt

import android.content.Context
import com.myproject.notes.database.NotesDao
import com.myproject.notes.database.NotesDatabase
import com.myproject.notes.repository.Repository
import com.myproject.notes.viewModel.NotesViewModel
import com.myproject.notes.viewModel.NotesViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getViewModelFactory(repository: Repository): NotesViewModelFactory{
        return NotesViewModelFactory(repository)
    }

    @Singleton
    @Provides
    fun getViewModel(repository: Repository): NotesViewModel {
        return NotesViewModel(repository)
    }

    @Singleton
    @Provides
    fun getRepository(dao: NotesDao): Repository {
        return Repository(dao)
    }

    @Singleton
    @Provides
    fun getDao(database: NotesDatabase): NotesDao {
        return database.getDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase {
        return NotesDatabase.getInstance(context)
    }
}