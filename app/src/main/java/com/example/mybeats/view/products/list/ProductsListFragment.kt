package com.example.mybeats.view.products.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mybeats.R
import com.example.mybeats.databinding.FragmentProductsListBinding

class ProductsListFragment : Fragment() {

    private lateinit var binding: FragmentProductsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsListBinding.inflate(layoutInflater)
        return binding.root
    }
}