package com.example.mybeats.view.products.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybeats.R
import com.example.mybeats.data.model.Product
import com.example.mybeats.databinding.FragmentProductsListBinding
import com.example.mybeats.view.products.list.adapter.ProductsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsListFragment : Fragment() {

    private lateinit var binding: FragmentProductsListBinding
    private val viewModel: ProductsViewModel by viewModel()
    private val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter() { navigateToDetail(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchView()
        setupObservers()
        setupRecyclerView()
        viewModel.getProducts()
    }

    private fun setupSearchView() {
        binding.toolbar.inflateMenu(R.menu.search_menu)
        val menuItem = binding.toolbar.menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.queryHint = requireActivity().getString(R.string.label_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productsAdapter.filterList(newText)
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        val linearLayout = LinearLayoutManager(requireActivity())
        binding.rvProducts.apply {
            layoutManager = linearLayout
            adapter = productsAdapter
        }
    }

    private fun setupObservers() {
        viewModel.products.observe(requireActivity()) {
            productsAdapter.setData(it)
        }
    }

    private fun navigateToDetail(product: Product){
        val direction = ProductsListFragmentDirections.actionProductsListFragmentToProductDetailFragment(product)
        binding.root.findNavController().navigate(direction)
    }
}