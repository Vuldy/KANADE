package com.projects.kanade.di

import com.projects.kanade.data.AntrianRepository
import com.projects.kanade.data.UserRepository

object Injection {
    fun provideRepository(): UserRepository {
        return UserRepository.getInstance()
    }

    fun provideRepository2(): AntrianRepository {
        return AntrianRepository.getInstance()
    }

    /*fun provideFirebaseRepository(): AntrianRepository {
        return FirebaseRepository.getInstance()
    }*/
}