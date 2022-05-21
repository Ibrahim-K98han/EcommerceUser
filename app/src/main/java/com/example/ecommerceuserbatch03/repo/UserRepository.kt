package com.example.ecommerceuserbatch03.repo

import com.example.ecommerceuserbatch03.models.EcomUser
import com.example.ecommerceuserbatch03.utils.collectionUser
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    val db = FirebaseFirestore.getInstance()

    fun insertNewUser(ecomUser: EcomUser) {
        db.collection(collectionUser).document(ecomUser.userId!!)
            .set(ecomUser)
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }

    fun updateLastSignInTimeAndOnlineStatus(userId: String, time: Long) {
        db.collection(collectionUser).document(userId)
            .update(mapOf("userLastSignInTimeStamp" to time, "isOnline" to true))
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }

    fun updateLastAppExitTimeAndOnlineStatus(time: Long, userId: String, status: Boolean, callback: (() -> Unit)? = null) {
        db.collection(collectionUser).document(userId)
            .update(mapOf("lastUsageTimestamp" to time, "isOnline" to status))
            .addOnSuccessListener {
                callback?.invoke()
            }.addOnFailureListener {

            }
    }

    fun updateOnlineStatus(userId: String, status: Boolean, callback: (() -> Unit)? = null) {
        db.collection(collectionUser).document(userId)
            .update("isOnline", status)
            .addOnSuccessListener {
                callback?.let { it1 -> it1() }
            }.addOnFailureListener {

            }
    }
}