package com.example.mybeats.view.products.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mybeats.R
import com.example.mybeats.data.model.Product
import com.example.mybeats.databinding.ProductItemListBinding
import java.util.*

class ProductsAdapter(val onItemClick: (Product) -> Unit): ListAdapter<Product, ProductsAdapter.ProductsAdapterViewHolder>(ProductsDiffUtil()) {

    private var productsList = mutableListOf<Product>()

    inner class ProductsAdapterViewHolder(
        private val binding: ProductItemListBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Product){
            binding.tvProductName.text = item.model
            binding.tvRating.text = item.rating.toString()
            binding.tvProductPrice.text = binding.root.context.resources.getString(R.string.BRL, item.price)
            binding.tvReviews.text = binding.root.context.resources.getQuantityString(R.plurals.reviews_count, item.reviews, item.reviews)

            Glide.with(binding.root)
                .load(item.image)
                .into(binding.ivProduct)

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapterViewHolder {
        return ProductsAdapterViewHolder(
            ProductItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holderAdapter: ProductsAdapterViewHolder, position: Int) {
        holderAdapter.bind(getItem(position))
    }

    fun filterList(filterText: String?){
        val filteredList = mutableListOf<Product>()
        if (filterText.isNullOrEmpty()){
            filteredList.addAll(productsList)
        } else {
            val result = productsList.filter { it.model.lowercase(Locale.getDefault()).contains(filterText.lowercase(Locale.getDefault())) }
            filteredList.addAll(result)
        }
        submitList(filteredList)
    }

    fun setData(list: List<Product>){
        productsList = list.toMutableList()
        submitList(list)
    }

}

class ProductsDiffUtil: DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}
