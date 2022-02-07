package com.example.mybeats.view.products.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mybeats.data.model.Product
import com.example.mybeats.databinding.ProductItemListBinding

class ProductsAdapter(val onItemClick: (Product) -> Unit): ListAdapter<Product, ProductsAdapter.ProductsAdapterViewHolder>(ProductsDiffUtil()) {

    inner class ProductsAdapterViewHolder(
        private val binding: ProductItemListBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Product){
            binding.tvProductName.text = item.model
            binding.tvRating.text = item.rating.toString()
            binding.tvProductPrice.text = "R$ " + item.price
            binding.tvReviews.text = item.reviews.toString() + " Reviews"

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
}

class ProductsDiffUtil: DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}
