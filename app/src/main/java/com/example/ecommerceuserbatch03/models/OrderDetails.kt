package com.example.ecommerceuserbatch03.models

data class OrderDetails(
    var productId:String?=null,
    var productName:String? = null,
    var productPrice:Double = 0.0,
    var productQuantity:Double = 0.0
)
