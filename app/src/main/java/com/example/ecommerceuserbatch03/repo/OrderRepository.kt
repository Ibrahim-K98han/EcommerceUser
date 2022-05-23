package com.example.ecommerceuserbatch03.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceuserbatch03.models.OrderSettings
import com.example.ecommerceuserbatch03.utils.collectionOrderSettings
import com.example.ecommerceuserbatch03.utils.documentOrderConstants
import com.google.firebase.firestore.FirebaseFirestore

class OrderRepository {
    val db = FirebaseFirestore.getInstance()
    fun getOrderSettings() : LiveData<OrderSettings> {
        val settingsLD = MutableLiveData<OrderSettings>()
        db.collection(collectionOrderSettings)
            .document(documentOrderConstants)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                settingsLD.value = value!!.toObject(OrderSettings::class.java)
            }
        return settingsLD
    }
}