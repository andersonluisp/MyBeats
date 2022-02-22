package com.example.mybeats.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val model: String,
    val imageUrl: String,
    val rating: Double,
    val reviews: Int,
    val price: String,
    val connection: String,
    val compatibility: String,
    val powerSupply: String,
    val autonomy: String,
    val height: String,
    val capture: String
) : Parcelable
