package com.example.ecommerceuserbatch03.vidwmodels

import androidx.lifecycle.ViewModel
import com.example.ecommerceuserbatch03.models.CartItem
import com.example.ecommerceuserbatch03.repo.UserRepository

class UserViewModel : ViewModel() {
    val userRepository = UserRepository()

    fun getCartItems(userId: String) = userRepository.getAllCartItems(userId)
    fun addToCart(userId: String, cartItem: CartItem) = userRepository.addToCart(userId, cartItem)
    fun removeFromCart(userId: String, productId: String) = userRepository.removeFromCart(userId, productId)
}