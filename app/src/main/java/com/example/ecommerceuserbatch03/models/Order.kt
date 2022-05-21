package com.example.ecommerceuserbatch03.models

import com.example.ecommerceuserbatch03.utils.OrderStatus
import com.example.ecommerceuserbatch03.utils.PaymentMethod
import java.sql.Timestamp

data class Order(
    var orderId:String?=null,
    var userId:String?=null,
    var orderDate:Timestamp?=null,
    var deliveryCharge:Int = 0,
    var discount:Int = 0,
    var vat:Int = 0,
    var orderStatus:String = OrderStatus.pending,
    var paymentMethod: String = PaymentMethod.cod,
    var deliveryAddress:String?= null
)
