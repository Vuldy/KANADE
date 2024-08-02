package com.projects.kanade.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.projects.kanade.model.AntrianUser
import com.projects.kanade.model.DummyDataSource
import com.projects.kanade.model.JanjiTemu
import com.projects.kanade.model.Resource
import com.projects.kanade.model.User
import com.projects.kanade.model.UserDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await

class UserRepository () {

    private val savedUser = mutableListOf<User>()

    var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    var dbUser: CollectionReference = db.collection("User")

    var auth = FirebaseAuth.getInstance()

    init {
        if (savedUser.isEmpty()) {
            DummyDataSource.UserDetails.forEach {
                savedUser.add(it)
            }
        }
    }

    fun loginUser(username: String, password: String) {
        auth.signInWithEmailAndPassword("$username@gmail.com", password)
            /*.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                }
                else {

                }
            }*/

        /*if (valid) {
            val result = getUserDetailsByUsername(username)
            LoggedUser.nama = result.data!!.nama
            LoggedUser.telp = result.data.telp
            LoggedUser.username = result.data.username
            LoggedUser.umur = result.data.umur
            LoggedUser.gender = result.data.gender
            LoggedUser.id = result.data.id
            LoggedUser.trust = result.data.trust
            LoggedUser.accesslevel = result.data.access
            LoggedUser.currentAntrianID = "${result.data.nama}${result.data.username}"
        }

        return valid*/
    }

    fun registerUser(username: String, nama: String, telp: String, password: String, access: Int, umur: Int, gender: Int) {
        auth.createUserWithEmailAndPassword("$username@gmail.com", password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    saveUserData(1, username.lowercase(), nama, telp, /*password,*/ access, 0, umur, gender)
                    // Handle successful registration
                } else {
                    // Handle registration failure
                }
            }
    }

    fun saveUserData(id: Long, username: String, nama: String, telp: String, /*password: String,*/ access: Int, trust: Int, umur: Int, gender: Int) {
        val userData = UserDB(id, username, nama, telp, /*password*/ access, trust, umur, gender)
        val userRef = dbUser.document(username)

        userRef.set(userData)
            .addOnSuccessListener {
                // User data saved successfully
            }
            .addOnFailureListener {
                // Error occurred while saving user data
            }
    }

    /* fun getAllUser(): Flow<List<User>> {
        return flowOf(savedUser)
    }*/

    suspend fun getAllUser(): Resource<List<UserDB>> {
        val result: List<UserDB>
        return try {
            result = dbUser.get().await().map {
                it.toObject(UserDB::class.java)
            }
            Resource.Success(result)
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(e.message.toString())
        }
    }

    suspend fun getAllUserExAdmin(): Resource<List<UserDB>> {
        val result: List<UserDB>
        return try {
            result = dbUser.get().await().map {
                it.toObject(UserDB::class.java)
            }
            Resource.Success(result.filter { it.access != 3 })
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(e.message.toString())
        }
    }

    fun addUser(
        username: String,
        nama: String,
        telp: String,
        password: String,
        age: Int,
        gender: Int,
    ) {
        val user = UserDB(1,username, nama, telp, /*password,*/1, 0, age, gender)

        dbUser.document(username).set(user)
    }

    fun updateUser(
        username: String,
        nama: String,
        telp: String,
        access: Int,
        password: String,
        umur: Int,
    ) {
        dbUser.document(username).update("username", username, "nama", nama, "telp", telp, "access", access, "password", password, "umur", umur)
    }



    /*fun addUser(inputUsername: String, inputNama: String, inputTelp: String, inputPassword: String ){
        val max: User = savedUser.maxBy { it.id }

        savedUser.add(User((max.id + 1), inputUsername, inputNama, inputTelp, inputPassword, 1, AntrianUser(false, 0, "",)))
    }*/

    /*fun getUserfromPoli(poli: String): Flow<List<User>> {
        return flowOf(savedUser.filter{ it.antri.poli == poli }.sortedBy { it.antri.nomor })
    }*/

    suspend fun getUserDetailsByUsername(username: String): Resource<UserDB> {
        val data = getAllUser()
        val result: UserDB? = data.data!!.find { it.username == username}
        return if (result!= null) {
            Resource.Success(result)
        } else {
            Resource.Success(UserDB(0,LoggedUser.nama,"Tidak ada data", "Tidak ada data", 1, ))
        }
    }

    /*fun getUserDetailsById(userId: Long): User {
        return savedUser.first {
            it.id == userId
        }
    }*/

    suspend fun findUsername(username: String): Boolean {
        val data = getAllUser()
        val result: UserDB? = data.data!!.find { it.username == username}
        return result != null
    }

    /*fun findUsername(dataUsername: String): Boolean {
        val result: User? = savedUser.find { it.username == dataUsername }
        return result != null
    }*/

    /*fun getUserDetailsByUsername(dataUsername: String): User {
        return savedUser.first {
            it.username == dataUsername
        }
    }*/

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository =
            instance ?: synchronized(this) {
                UserRepository().apply {
                    instance = this
                }
            }
    }

}