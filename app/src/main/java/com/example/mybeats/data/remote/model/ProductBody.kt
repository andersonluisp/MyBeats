package com.example.mybeats.data.remote.model

data class ProductBody(
    val autonomy: String,
    val capture: String,
    val compatibility: String,
    val connection: String,
    val height: String,
    val imageUrl: String,
    val model: String,
    val powerSupply: String,
    val price: String,
    val rating: Double,
    val reviews: Int
)