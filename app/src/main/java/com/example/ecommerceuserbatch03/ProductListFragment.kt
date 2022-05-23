package com.example.ecommerceuserbatch03

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceuserbatch03.adapters.ProductAdapter
import com.example.ecommerceuserbatch03.databinding.FragmentProductListBinding
import com.example.ecommerceuserbatch03.models.CartItem
import com.example.ecommerceuserbatch03.models.Product
import com.example.ecommerceuserbatch03.utils.CartAction
import com.example.ecommerceuserbatch03.vidwmodels.LoginViewModel
import com.example.ecommerceuserbatch03.vidwmodels.ProductViewModel
import com.example.ecommerceuserbatch03.vidwmodels.UserViewModel

class ProductListFragment : Fragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var binding: FragmentProductListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.product_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(item)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        val adapter = ProductAdapter {action, cartItem ->
            performCartAction(action, cartItem)
        }
        val glm = GridLayoutManager(requireActivity(), 2)
        binding.productRV.layoutManager = glm
        binding.productRV.adapter = adapter
        loginViewModel.authStateLD.observe(viewLifecycleOwner) {
            if (it == LoginViewModel.AuthState.UNAUTHENTICATED) {
                findNavController()
                    .navigate(R.id.action_productListFragment_to_loginFragment)
            }
        }

        userViewModel.getCartItems(loginViewModel.firebaseAuth.currentUser?.uid!!)
            .observe(viewLifecycleOwner) { cartList ->
                productViewModel.getProducts().observe(viewLifecycleOwner) {productList ->
                    if (productList.isEmpty()) {
                        binding.mProgressBar.visibility = View.VISIBLE
                    } else {
                        binding.mProgressBar.visibility = View.GONE
                    }
                    val tempList = mutableListOf<Product>()
                    for (product in productList) {
                        for (cart in cartList) {
                            if (product.id == cart.productId) {
                                product.inCart = true
                            }
                        }
                        tempList.add(product)
                    }
                    adapter.submitList(tempList)
                }
            }



        return binding.root
    }

    private fun performCartAction(action: String, cartItem: CartItem) {
        when(action) {
            CartAction.addToCart -> {
                userViewModel.addToCart(loginViewModel.firebaseAuth.currentUser?.uid!!, cartItem)
            }
            CartAction.removeFromCart -> {
                userViewModel.removeFromCart(loginViewModel.firebaseAuth.currentUser?.uid!!, cartItem.productId!!)
            }
        }
    }

}