package com.example.ecommerceuserbatch03.utils

const val TAG = "MadBatch03"
const val collectionAdmin = "Admins"
const val collectionProduct = "Product"
const val collectionPurchase = "Purchase"
const val collectionCategory = "Category"
const val collectionUser = "User"
const val collectionOrder= "Order"
const val collectionCart = "CartItems"
const val collectionOrderDetails= "Order Details"
const val collectionOrderSettings = "Order Settings"
const val collectionOrderConstants = "OrderConstants"

class PaymentMethod{
    companion object{
        const val cod = "Cash On Delivery"
        const val online = "Online"
    }
}
class OrderStatus{
    companion object{
        const val pending = "Pending"
        const val delivery = "Delivery"
        const val cancelled = "Cancelled"
    }
}

class CartAction {
    companion object {
        const val addToCart = "Add to Cart"
        const val removeFromCart = "Remove from Cart"
    }
}