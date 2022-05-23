package com.example.ecommerceuserbatch03

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceuserbatch03.adapters.CartItemAdapter
import com.example.ecommerceuserbatch03.databinding.FragmentCartListBinding
import com.example.ecommerceuserbatch03.models.CartItem
import com.example.ecommerceuserbatch03.vidwmodels.LoginViewModel
import com.example.ecommerceuserbatch03.vidwmodels.ProductViewModel
import com.example.ecommerceuserbatch03.vidwmodels.UserViewModel

class CartListFragment : Fragment() {

    private lateinit var binding: FragmentCartListBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartListBinding.inflate(inflater, container, false)
        val adapter = CartItemAdapter {cartItem ->
            userViewModel.updateCartItem(
                userId = loginViewModel.firebaseAuth.currentUser?.uid!!,
                productId = cartItem.productId!!,
                qty = cartItem.quantity
            )
        }
        binding.cartRV.layoutManager = LinearLayoutManager(requireActivity())
        binding.cartRV.adapter = adapter
        userViewModel.getCartItems(loginViewModel.firebaseAuth.currentUser?.uid!!)
            .observe(viewLifecycleOwner) {
                Log.e("CartItem", "updated", )
                adapter.submitList(it)
                updateTotal(it)
            }
        binding.checkoutBtn.setOnClickListener {
            findNavController().navigate(R.id.action_cartListFragment_to_checkOutFragment)
        }
        return binding.root
    }

    private fun updateTotal(it: List<CartItem>?) {
        var total = 0.0
        binding.checkoutBtn.isEnabled = !it.isNullOrEmpty()
        it?.let {
            it.forEach {
                total += it.quantity * it.price!!
            }
        }
        binding.cartTotalPriceTV.text = "Total: $total"
    }

}