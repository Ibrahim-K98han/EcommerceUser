package com.example.ecommerceuserbatch03.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.example.ecommerceuserbatch03.models.Product
import com.example.ecommerceuserbatch03.models.Puarchase
import com.example.ecommerceuserbatch03.utils.TAG
import com.example.ecommerceuserbatch03.utils.collectionCategory
import com.example.ecommerceuserbatch03.utils.collectionProduct
import com.example.ecommerceuserbatch03.utils.collectionPurchase

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()

    fun addNewProduct(product: Product, purchase: Puarchase, callback: (String) -> Unit) {
        val wb = db.batch()
        val productDoc = db.collection(collectionProduct).document()
        val purchaseDoc = db.collection(collectionPurchase).document()
        product.id = productDoc.id
        purchase.purchaseId = purchaseDoc.id
        purchase.productId = product.id
        wb.set(productDoc, product)
        wb.set(purchaseDoc, purchase)
        wb.commit().addOnSuccessListener {
            callback("Success")
        }.addOnFailureListener {
            callback("Failure")
        }
    }

    fun addRePurchase(purchase: Puarchase) {
        val purchaseDoc = db.collection(collectionPurchase).document()
        purchase.purchaseId = purchaseDoc.id
        purchaseDoc.set(purchase)
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }

    fun getAllProducts() : LiveData<List<Product>> {
        val productLD = MutableLiveData<List<Product>>()
        db.collection(collectionProduct)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val tempList = mutableListOf<Product>()
                for (doc in value!!.documents) {
                    doc.toObject(Product::class.java)?.let { tempList.add(it) }
                }
                Log.e(TAG, "getAllCategories: ${tempList.size}" )
                productLD.value = tempList
            }
        return productLD
    }

    fun getProductById(id: String) : LiveData<Product> {
        val productLD = MutableLiveData<Product>()
        db.collection(collectionProduct).document(id)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                productLD.value = value?.toObject(Product::class.java)
            }
        return productLD
    }

    fun getPurchaseByProductId(id: String) : LiveData<List<Puarchase>> {
        val purchaseLD = MutableLiveData<List<Puarchase>>()
        db.collection(collectionPurchase)
            .whereEqualTo("productId", id)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<Puarchase>()
                for (doc in value!!.documents) {
                    doc.toObject(Puarchase::class.java)?.let { tempList.add(it) }
                }
                purchaseLD.value = tempList
            }
        return purchaseLD
    }

    fun getAllCategories() : LiveData<List<String>> {
        val catLD = MutableLiveData<List<String>>()
        db.collection(collectionCategory)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val tempList = mutableListOf<String>()
                for (doc in value!!.documents) {
                    tempList.add(doc.get("name").toString())
                }
                Log.e(TAG, "getAllCategories: ${tempList.size}" )
                catLD.value = tempList
            }
        return catLD
    }

}