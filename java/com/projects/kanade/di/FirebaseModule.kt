package com.projects.kanade.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/* @Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun providesFireStore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseRepository(
        firestore: FirebaseFirestore,
    ): FirebaseRepository {
        return FirebaseRepositoryImpl(
            fireStore = firestore,
            )
    }

}*/