package com.example.ecommerceuserbatch03.vidwmodels

import androidx.lifecycle.ViewModel
import com.example.ecommerceuserbatch03.repo.OrderRepository

class OrderViewModel : ViewModel() {
    private val orderRepository = OrderRepository()

    fun getOrderSettings() = orderRepository.getOrderSettings()
}