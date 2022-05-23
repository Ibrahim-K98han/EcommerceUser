package com.example.ecommerceuserbatch03

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ecommerceuserbatch03.databinding.CartItemSimpleRowBinding
import com.example.ecommerceuserbatch03.databinding.FragmentOrderConfirmationBinding
import com.example.ecommerceuserbatch03.models.CartItem
import com.example.ecommerceuserbatch03.vidwmodels.OrderViewModel
import com.example.ecommerceuserbatch03.vidwmodels.UserViewModel


class OrderConfirmationFragment : Fragment() {
    private lateinit var binding: FragmentOrderConfirmationBinding
    private val orderViewModel: OrderViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderConfirmationBinding.inflate(inflater, container, false)
        val bundle = arguments
        bundle?.let {
            val address = it.getString("address")
            val paymentMethod = it.getString("payment")
            binding.deliveryAddressTV.text = address
            binding.paymentMethodTV.text = paymentMethod
        }
        userViewModel.getCartItems(userViewModel.getCurrentUserId()!!)
            .observe(viewLifecycleOwner) {
                createCartItemList(it, inflater)
                binding.totalTV.text = "BDT${userViewModel.getCartTotalPrice(it)}"
            }
        orderViewModel.getOrderSettings().observe(viewLifecycleOwner) {
            binding.deliveryChargeTV.text = "${it.deliverCharge}"
            binding.discountLabelTV.text = "Discount(${it.discount}%)"
            binding.vatLabelTV.text = "VAT(${it.vat}%)"
        }
        return binding.root
    }

    private fun createCartItemList(it: List<CartItem>?, inflater: LayoutInflater) {
        it.let { cartList ->
            cartList!!.forEach { cartItem ->
                val cartBinding = CartItemSimpleRowBinding.inflate(inflater)
                cartBinding.item = cartItem
                binding.cartItemsLinearLayout.addView(cartBinding.root)
            }
        }
    }
}