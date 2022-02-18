package com.example.mybeats.view.products.list

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybeats.R
import com.example.mybeats.data.model.Product
import com.example.mybeats.databinding.FragmentProductsListBinding
import com.example.mybeats.view.extension.invisible
import com.example.mybeats.view.extension.visible
import com.example.mybeats.view.products.list.adapter.ProductsAdapter
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsListFragment : Fragment() {

    private lateinit var binding: FragmentProductsListBinding
    private val viewModel: ProductsViewModel by viewModel()
    private val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter { navigateToDetail(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsListBinding.inflate(layoutInflater)
        setupActionBar()

        if (savedInstanceState == null) {
            viewModel.getProducts()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()
        setupListeners()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        setupSearchView(menu)
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            elevation = 0f
            setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.toolbar_color
                    )
                )
            )
            title = ""
        }
    }

    private fun setupListeners() {
        binding.btnTryAgain.setOnClickListener {
            viewModel.getProducts()
        }
    }

    private fun setupSearchView(menu: Menu) {
        val menuItem = menu.findItem(R.id.action_search)
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
        lifecycleScope.launchWhenCreated {
            viewModel.productsState.collect {
                when (it) {
                    is ProductsViewModel.ViewState.Success -> showSuccessState(it.data)
                    is ProductsViewModel.ViewState.Loading -> showLoadingState()
                    is ProductsViewModel.ViewState.Error -> showErrorState()
                    is ProductsViewModel.ViewState.Initial -> showInitialState()
                }
            }
        }
    }

    private fun showInitialState() {
        binding.apply {
            groupError.invisible()
            containerList.invisible()
            circularProgressBar.invisible()
        }
    }

    private fun showSuccessState(productsList: List<Product>) {
        productsAdapter.setData(productsList)
        binding.apply {
            groupError.invisible()
            containerList.visible()
            circularProgressBar.invisible()
            (activity as AppCompatActivity).supportActionBar?.show()
        }
    }

    private fun showLoadingState() {
        binding.apply {
            groupError.invisible()
            circularProgressBar.visible()
            containerList.invisible()
        }
    }

    private fun showErrorState() {
        binding.apply {
            groupError.visible()
            circularProgressBar.invisible()
            containerList.invisible()
            (activity as AppCompatActivity).supportActionBar?.hide()
        }
    }

    private fun navigateToDetail(product: Product) {
        val direction =
            ProductsListFragmentDirections.actionProductsListFragmentToProductDetailFragment(product)
        binding.root.findNavController().navigate(direction)
    }
}
