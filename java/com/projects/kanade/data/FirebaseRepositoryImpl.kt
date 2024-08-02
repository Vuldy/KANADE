package com.projects.kanade.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.projects.kanade.model.JanjiTemu
import com.projects.kanade.model.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/* class FirebaseRepositoryImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : FirebaseRepository {

    override suspend fun getAllAntrian(): Resource<List<JanjiTemu>> {
        val result: List<JanjiTemu>
        return try {
            result = fireStore.collection("Antrian").get().await().map {
                it.toObject(JanjiTemu::class.java)
            }
            Resource.Success(result)
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun getAntrianUser(nama: String): Resource<JanjiTemu> {
        var result = JanjiTemu()

        return try {
            fireStore.collection("Antrian")
                .whereArrayContains("namaPasien", nama).limit(1).get().await().map{
                    val hasil = it.toObject(JanjiTemu::class.java)
                    result = hasil
                }

            Resource.Success(result)
        } catch (e : FirebaseFirestoreException) {
            Resource.Error(e.message.toString())
        }
    }

}*/